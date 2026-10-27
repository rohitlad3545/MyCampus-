package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Department;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.DepartmentService;

@RestController
@RequestMapping("/api/v1/admin/department")
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllDepartments() {

        return departmentService.getAllDepartments();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getDepartmentById(
            @PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }


    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addDepartment(
            @RequestBody Department department) {

        return departmentService.addDepartment(
                department
        );
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department department) {

        return departmentService.updateDepartment(
                id,
                department
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteDepartment(
            @PathVariable Long id) {

        return departmentService.deleteDepartment(id);
    }

}