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

import com.rohit.entites.Subject;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.SubjectService;

@RestController
@RequestMapping("/api/v1/admin/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getSubjectById(
            @PathVariable long id) {

        return subjectService.getSubjectById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addSubject(
            @RequestBody Subject subject) {

        return subjectService.addSubject(subject);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateSubject(
            @PathVariable long id,
            @RequestBody Subject subject) {

        return subjectService.updateSubject(id, subject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteSubject(
            @PathVariable long id) {

        return subjectService.deleteSubject(id);
    }
}