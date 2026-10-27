
package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.SubjectService;

@RestController
@RequestMapping("/api/v1/faculty/subjects")
public class FacultySubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/{facultyId}")
    public ResponseEntity<CustomResponse> getSubjectsByFaculty(
            @PathVariable Long facultyId) {

        return subjectService.getSubjectsByFaculty(facultyId);
    }
}
