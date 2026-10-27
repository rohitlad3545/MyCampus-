
package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.FacultyStudentService;

@RestController
@RequestMapping("/api/v1/faculty/student")
public class FacultyStudentController {

    @Autowired
    FacultyStudentService facultyStudentService;

    @GetMapping("/all/{facultyId}")
    public ResponseEntity<CustomResponse> getStudentsByFaculty(
            @PathVariable Long facultyId) {

        return facultyStudentService.getStudentsByFaculty(facultyId);
        
        
    }
}
