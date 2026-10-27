package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.HODSubjectService;

@RestController
@RequestMapping("/api/v1/hod/subject")
public class HODSubjectController {

    @Autowired
    HODSubjectService hodSubjectService;


    @GetMapping("/all/{id}")
    public ResponseEntity<CustomResponse> getSubjects(
            @PathVariable long id) {

        return hodSubjectService
                .getSubjectsByHOD(id);
    }
}