
package com.rohit.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.rohit.entites.FeePayment;
import com.rohit.entites.Student;
import com.rohit.entites.StudentFee;
import com.rohit.repositories.FeePaymentRepository;
import com.rohit.repositories.StudentFeeRepository;
import com.rohit.repositories.StudentRepository;

@Service
public class RazorpayPaymentService {

    private final StudentRepository studentRepository;
    private final StudentFeeRepository studentFeeRepository;
    private final FeePaymentRepository paymentRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public RazorpayPaymentService(
            StudentRepository studentRepository,
            StudentFeeRepository studentFeeRepository,
            FeePaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.studentFeeRepository = studentFeeRepository;
        this.paymentRepository = paymentRepository;
    }

    private RazorpayClient getClient() throws Exception {
        return new RazorpayClient(keyId, keySecret);
    }

    private Student getCurrentStudent(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Please log in");
        }

        return studentRepository
            .findByUserUsername(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "No student is linked to this account"));
    }

    private StudentFee getOwnedFee(Long feeId, Student student) {
        StudentFee fee = studentFeeRepository.findById(feeId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Fee not found"));

        if (fee.getStudent().getId() != student.getId()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You cannot access another student's fee");
        }

        return fee;
    }

    private long toPaise(double rupees) {
        if (!Double.isFinite(rupees) || rupees <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No outstanding balance to pay");
        }

        try {
            return BigDecimal.valueOf(rupees)
                .setScale(2, RoundingMode.UNNECESSARY)
                .movePointRight(2)
                .longValueExact();
        } catch (ArithmeticException ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Fee amount must have at most two decimal places");
        }
    }

    private Map<String, Object> feeResponse(StudentFee fee) {
        return Map.of(
            "id", fee.getId(),
            "totalAmount", fee.getTotalAmount(),
            "discount", fee.getDiscount(),
            "lateFee", fee.getLateFee(),
            "amountPaid", fee.getAmountPaid(),
            "balance", fee.getBalance(),
            "status", fee.getStatus(),
            "dueDate", fee.getDueDate().toString()
        );
    }

    public List<Map<String, Object>> getMyFees(Principal principal) {
        Student student = getCurrentStudent(principal);

        return studentFeeRepository
            .findByStudentId(student.getId())
            .stream()
            .map(this::feeResponse)
            .toList();
    }

    public Map<String, Object> createOrder(
            Long feeId, Principal principal) {

        Student student = getCurrentStudent(principal);
        StudentFee fee = getOwnedFee(feeId, student);

        if (fee.getBalance() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "This fee is already paid");
        }

        try {
            long amountPaise = toPaise(fee.getBalance());

            JSONObject options = new JSONObject();
            options.put("amount", amountPaise);
            options.put("currency", "INR");
            options.put("receipt",
                "fee_" + fee.getId() + "_" + System.currentTimeMillis());

            Order order = getClient().orders.create(options);

            FeePayment payment = new FeePayment();
            payment.setStudentFee(fee);
            payment.setRazorpayOrderId(order.get("id"));
            payment.setAmountPaise(amountPaise);
            payment.setCurrency("INR");
            payment.setStatus("CREATED");

            paymentRepository.save(payment);

            return Map.of(
                "keyId", keyId,
                "orderId", order.get("id"),
                "amount", amountPaise,
                "currency", "INR",
                "feeId", fee.getId(),
                "studentName", student.getName()
            );

        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "Could not create Razorpay order");
        }
    }

    private boolean verifySignature(
            String orderId, String paymentId, String receivedSignature) {
        try {
            String message = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                keySecret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"));

            byte[] hash = mac.doFinal(
                message.getBytes(StandardCharsets.UTF_8));

            StringBuilder expected = new StringBuilder();
            for (byte b : hash) {
                expected.append(String.format("%02x", b & 0xff));
            }

            return MessageDigest.isEqual(
                expected.toString().getBytes(StandardCharsets.US_ASCII),
                receivedSignature.getBytes(StandardCharsets.US_ASCII));

        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Could not verify payment signature");
        }
    }

    @Transactional
    public Map<String, Object> verifyPayment(
            String orderId,
            String paymentId,
            String signature,
            Principal principal) {

        if (orderId == null || paymentId == null || signature == null
                || orderId.isBlank() || paymentId.isBlank()
                || signature.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Payment verification details are missing");
        }

        Student student = getCurrentStudent(principal);

        FeePayment payment = paymentRepository
            .findByRazorpayOrderId(orderId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Payment order not found"));

        StudentFee fee = studentFeeRepository
            .findByIdForUpdate(payment.getStudentFee().getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Fee not found"));

        if (fee.getStudent().getId() != student.getId()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "This payment does not belong to you");
        }

        if ("PAID".equals(payment.getStatus())) {
            if (!paymentId.equals(payment.getRazorpayPaymentId())) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Payment ID mismatch");
            }

            return Map.of(
                "message", "Payment already recorded",
                "paymentId", paymentId,
                "fee", feeResponse(fee));
        }

        if (!verifySignature(orderId, paymentId, signature)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid payment signature");
        }

        if (paymentRepository.existsByRazorpayPaymentId(paymentId)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "This payment ID has already been used");
        }

        try {
            Payment actual = getClient().payments.fetch(paymentId);

            String actualOrderId = actual.get("order_id");
            String actualStatus = actual.get("status");
            String currency = actual.get("currency");
            Number amount = actual.get("amount");

            if (!orderId.equals(actualOrderId)) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Order ID mismatch");
            }

            if (!"captured".equals(actualStatus)) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Payment is not captured yet");
            }

            if (!"INR".equals(currency) || amount == null
                    || amount.longValue() != payment.getAmountPaise()) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Payment amount or currency mismatch");
            }

            long balancePaise = Math.round(fee.getBalance() * 100);

            if (balancePaise < payment.getAmountPaise()) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Balance changed; payment needs reconciliation");
            }

            BigDecimal paid = BigDecimal.valueOf(fee.getAmountPaid())
                .add(BigDecimal.valueOf(payment.getAmountPaise(), 2));

            BigDecimal balance = BigDecimal.valueOf(fee.getBalance())
                .subtract(BigDecimal.valueOf(payment.getAmountPaise(), 2));

            fee.setAmountPaid(
                paid.setScale(2, RoundingMode.HALF_UP).doubleValue());
            fee.setBalance(
                balance.setScale(2, RoundingMode.HALF_UP).doubleValue());
            fee.setStatus(balance.signum() == 0 ? "PAID" : "PARTIAL");

            payment.setRazorpayPaymentId(paymentId);
            payment.setStatus("PAID");
            payment.setPaidAt(LocalDateTime.now());

            studentFeeRepository.save(fee);
            paymentRepository.save(payment);

            return Map.of(
                "message", "Payment verified successfully",
                "paymentId", paymentId,
                "fee", feeResponse(fee));

        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "Could not confirm payment with Razorpay");
        }
    }

    public List<Map<String, Object>> getMyPaymentHistory(
            Principal principal) {

        Student student = getCurrentStudent(principal);

        return paymentRepository
            .findByStudentFeeStudentIdAndStatusOrderByCreatedAtDesc(
                student.getId(), "PAID")
            .stream()
            .map(p -> Map.<String, Object>of(
                "id", p.getId(),
                "feeId", p.getStudentFee().getId(),
                "orderId", p.getRazorpayOrderId(),
                "paymentId", p.getRazorpayPaymentId(),
                "amount", BigDecimal.valueOf(p.getAmountPaise(), 2),
                "status", p.getStatus(),
                "paidAt", p.getPaidAt().toString()))
            .toList();
    }
}
