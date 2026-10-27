package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.HODFacultyService;

@RestController
@RequestMapping("/api/v1/hod/faculty")
public class HODFacultyController {

    @Autowired
    HODFacultyService hodFacultyService;


    @GetMapping("/all/{id}")
    public ResponseEntity<CustomResponse> getFaculty(
            @PathVariable long id) {

        return hodFacultyService
                .getFacultyByHOD(id);
    }
}