package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.FeeStructure;
import com.rohit.entites.Student;
import com.rohit.entites.StudentFee;
import com.rohit.repositories.FeeStructureRepository;
import com.rohit.repositories.StudentFeeRepository;
import com.rohit.repositories.StudentRepository;
import com.rohit.response_wrapper.CustomResponse;

import jakarta.transaction.Transactional;

@Service
public class StudentFeeService {

    @Autowired
    private StudentFeeRepository studentFeeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    private ResponseEntity<CustomResponse> response(
            String message, Object data, HttpStatus status) {

        CustomResponse result = new CustomResponse();
        result.setMessage(message);
        result.setData(data);

        return new ResponseEntity<>(result, status);
    }

    public ResponseEntity<CustomResponse> getAll() {
        return response(
                "Student fees fetched successfully",
                studentFeeRepository.findAll(),
                HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> getById(Long id) {
        Optional<StudentFee> fee =
                studentFeeRepository.findById(id);

        if (fee.isEmpty()) {
            return response(
                    "Student fee not found",
                    null,
                    HttpStatus.NOT_FOUND);
        }

        return response(
                "Student fee fetched successfully",
                fee.get(),
                HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> getByStudentId(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            return response(
                    "Student not found",
                    null,
                    HttpStatus.NOT_FOUND);
        }

        return response(
                "Student fees fetched successfully",
                studentFeeRepository.findByStudentId(studentId),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<CustomResponse> add(StudentFee request) {

        if (request.getStudent() == null
                || request.getFeeStructure() == null) {
            return response(
                    "Student and fee structure are required",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Student> student =
                studentRepository.findById(
                        request.getStudent().getId());

        Optional<FeeStructure> structure =
                feeStructureRepository.findById(
                        request.getFeeStructure().getId());

        if (student.isEmpty()) {
            return response(
                    "Student not found",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        if (structure.isEmpty()) {
            return response(
                    "Fee structure not found",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        double total = structure.get().getTotalAmount();
        double discount = request.getDiscount();
        double lateFee = request.getLateFee();

        if (!Double.isFinite(discount)
                || !Double.isFinite(lateFee)
                || discount < 0
                || lateFee < 0
                || discount > total) {
            return response(
                    "Invalid discount or late fee",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        if (request.getDueDate() == null) {
            return response(
                    "Due date is required",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        double payable = total - discount + lateFee;

        StudentFee fee = new StudentFee();
        fee.setStudent(student.get());
        fee.setFeeStructure(structure.get());
        fee.setTotalAmount(total);
        fee.setDiscount(discount);
        fee.setLateFee(lateFee);
        fee.setAmountPaid(0.0);
        fee.setBalance(payable);
        fee.setDueDate(request.getDueDate());
        fee.setStatus("PENDING");

        StudentFee saved = studentFeeRepository.save(fee);

        return response(
                "Student fee assigned successfully",
                saved,
                HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<CustomResponse> delete(Long id) {

        Optional<StudentFee> existing =
                studentFeeRepository.findById(id);

        if (existing.isEmpty()) {
            return response(
                    "Student fee not found",
                    null,
                    HttpStatus.NOT_FOUND);
        }

        StudentFee fee = existing.get();

        if (fee.getAmountPaid() > 0) {
            return response(
                    "Cannot delete a fee record with payments",
                    null,
                    HttpStatus.CONFLICT);
        }

        studentFeeRepository.delete(fee);

        return response(
                "Student fee deleted successfully",
                null,
                HttpStatus.OK);
    }
    
    public ResponseEntity<CustomResponse> getMyFees(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            return response("Student not found", null, HttpStatus.NOT_FOUND);
        }

        return response(
            "Your fees fetched successfully",
            studentFeeRepository.findByStudentId(studentId),
            HttpStatus.OK
        );
    }
}