package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.StudentFeeService;

@RestController
@RequestMapping("/api/v1/student/fee")
public class StudentFeeStudentController {

    @Autowired
    private StudentFeeService studentFeeService;

    @GetMapping("/all/{studentId}")
    public ResponseEntity<CustomResponse> getMyFees(
            @PathVariable Long studentId) {
        return studentFeeService.getMyFees(studentId);
    }
}