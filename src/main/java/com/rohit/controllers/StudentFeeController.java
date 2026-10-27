package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.StudentFee;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.StudentFeeService;

@RestController
@RequestMapping("/api/v1/admin/student-fee")
public class StudentFeeController {

    @Autowired
    private StudentFeeService studentFeeService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAll() {
        return studentFeeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(
            @PathVariable Long id) {
        return studentFeeService.getById(id);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<CustomResponse> getByStudentId(
            @PathVariable Long studentId) {
        return studentFeeService.getByStudentId(studentId);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> add(
            @RequestBody StudentFee studentFee) {
        return studentFeeService.add(studentFee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(
            @PathVariable Long id) {
        return studentFeeService.delete(id);
    }
}