
package com.rohit.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.services.RazorpayPaymentService;

@RestController
@RequestMapping("/api/v1/student/payments")
public class RazorpayPaymentController {

    private final RazorpayPaymentService paymentService;

    public RazorpayPaymentController(
            RazorpayPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Get fees belonging to the currently logged-in student
    @GetMapping("/fees")
    public ResponseEntity<List<Map<String, Object>>> myFees(
            Principal principal) {

        return ResponseEntity.ok(
                paymentService.getMyFees(principal));
    }

    // Create a Razorpay order for a student's outstanding fee
    @PostMapping("/create-order/{feeId}")
    public ResponseEntity<Map<String, Object>> createOrder(
            @PathVariable Long feeId,
            Principal principal) {

        return ResponseEntity.ok(
                paymentService.createOrder(feeId, principal));
    }

    // Request received from Razorpay Checkout
    public record VerifyRequest(
            String razorpay_order_id,
            String razorpay_payment_id,
            String razorpay_signature) {
    }

    // Verify the payment and update the fee record
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(
            @RequestBody VerifyRequest request,
            Principal principal) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(
                        request.razorpay_order_id(),
                        request.razorpay_payment_id(),
                        request.razorpay_signature(),
                        principal));
    }

    // Get payment history belonging to the logged-in student
    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> history(
            Principal principal) {

        return ResponseEntity.ok(
                paymentService.getMyPaymentHistory(principal));
    }
}
