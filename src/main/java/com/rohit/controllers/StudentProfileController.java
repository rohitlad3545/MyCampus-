package com.rohit.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Student;
import com.rohit.repositories.StudentRepository;
import com.rohit.response_wrapper.CustomResponse;

@RestController
@RequestMapping("/api/v1/student/profile")
public class StudentProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{studentId}")
    public ResponseEntity<CustomResponse> getStudentProfile(
            @PathVariable Long studentId) {

        CustomResponse response = new CustomResponse();

        Optional<Student> student =
                studentRepository.findById(studentId);

        if (student.isEmpty()) {
            response.setMessage("Student not found");
            response.setData(null);

            return new ResponseEntity<>(
                    response, HttpStatus.NOT_FOUND);
        }

        response.setMessage("Student profile fetched successfully");
        response.setData(student.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}