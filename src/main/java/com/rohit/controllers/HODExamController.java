package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Exam;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.ExamService;

@RestController
@RequestMapping("/api/v1/hod/exam")
public class HODExamController {

    @Autowired
    ExamService examService;

    // GET ALL EXAMS

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllExams() {
        return examService.getAllExams();
    }


    // GET EXAM BY ID

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getExamById(
            @PathVariable long id) {

        return examService.getExamById(id);
    }


    // ADD EXAM

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addExam(
            @RequestBody Exam exam) {

        return examService.addExam(exam);
    }


    // UPDATE EXAM

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateExam(
            @PathVariable long id,
            @RequestBody Exam exam) {

        return examService.updateExam(id, exam);
    }


    // DELETE EXAM

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteExam(
            @PathVariable long id) {

        return examService.deleteExam(id);
    }
}