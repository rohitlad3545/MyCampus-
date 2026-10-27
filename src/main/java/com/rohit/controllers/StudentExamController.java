package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.ExamService;

@RestController
@RequestMapping("/api/v1/student/exams")
public class StudentExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/upcoming")
    public ResponseEntity<CustomResponse> getUpcomingExams() {
        return examService.getUpcomingExams();
    }
}