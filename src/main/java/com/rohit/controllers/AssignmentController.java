package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.entites.Assignment;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.AssignmentService;

@RestController
@RequestMapping("/api/v1/faculty/assignment")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllAssignments() {

        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAssignmentById(
            @PathVariable long id) {

        return assignmentService.getAssignmentById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addAssignment(
            @RequestBody Assignment assignment) {

        return assignmentService.addAssignment(assignment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateAssignment(
            @PathVariable long id,
            @RequestBody Assignment assignment) {

        return assignmentService.updateAssignment(
                id,
                assignment
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteAssignment(
            @PathVariable long id) {

        return assignmentService.deleteAssignment(id);
    }
}