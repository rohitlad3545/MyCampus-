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

import com.rohit.entites.Semester;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.SemesterService;

@RestController
@RequestMapping("/api/v1/admin/semester")
public class SemesterController {

    @Autowired
    SemesterService semesterService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllSemesters() {

        return semesterService.getAllSemesters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getSemesterById(
            @PathVariable long id) {

        return semesterService.getSemesterById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addSemester(
            @RequestBody Semester semester) {

        return semesterService.addSemester(semester);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateSemester(
            @PathVariable long id,
            @RequestBody Semester semester) {

        return semesterService.updateSemester(
                id,
                semester
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteSemester(
            @PathVariable long id) {

        return semesterService.deleteSemester(id);
    }
}