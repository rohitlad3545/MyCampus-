package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Faculty;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.FacultyService;

@RestController
@RequestMapping("/api/v1/admin/faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;


    // GET ALL FACULTY

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllFaculty() {

        return facultyService.getAllFaculty();
    }


    // GET FACULTY BY ID

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getFacultyById(
            @PathVariable Long id) {

        return facultyService.getFacultyById(id);
    }


    // ADD FACULTY

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addFaculty(
            @RequestBody Faculty faculty) {

        return facultyService.addFaculty(faculty);
    }


    // UPDATE FACULTY

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateFaculty(
            @PathVariable Long id,
            @RequestBody Faculty faculty) {

        return facultyService.updateFaculty(
                id,
                faculty
        );
    }


    // DELETE FACULTY

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteFaculty(
            @PathVariable Long id) {

        return facultyService.deleteFaculty(id);
    }

}