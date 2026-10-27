package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.AssignmentService;

@RestController
@RequestMapping("/api/v1/student/assignments")
public class StudentAssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }
}