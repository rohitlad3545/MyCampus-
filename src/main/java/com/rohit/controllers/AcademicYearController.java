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

import com.rohit.entites.AcademicYear;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.AcademicYearService;

@RestController
@RequestMapping("/api/v1/admin/academic-year")
public class AcademicYearController {

    @Autowired
    AcademicYearService academicYearService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllAcademicYears() {

        return academicYearService.getAllAcademicYears();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAcademicYearById(
            @PathVariable long id) {

        return academicYearService.getAcademicYearById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addAcademicYear(
            @RequestBody AcademicYear academicYear) {

        return academicYearService.addAcademicYear(academicYear);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateAcademicYear(
            @PathVariable long id,
            @RequestBody AcademicYear academicYear) {

        return academicYearService.updateAcademicYear(
                id,
                academicYear
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteAcademicYear(
            @PathVariable long id) {

        return academicYearService.deleteAcademicYear(id);
    }
}