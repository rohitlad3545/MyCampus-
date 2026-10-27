package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Student;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.StudentService;

@RestController
@RequestMapping("/api/v1/admin/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    // GET ALL STUDENTS

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllStudents() {

        return studentService.getAllStudents();
    }


    // GET STUDENT BY ID

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getStudentById(
            @PathVariable Long id) {

        return studentService.getStudentById(id);
    }


    // ADD STUDENT

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addStudent(
            @RequestBody Student student) {

        return studentService.addStudent(student);
    }


    // UPDATE STUDENT

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return studentService.updateStudent(
                id,
                student
        );
    }


    // DELETE STUDENT

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteStudent(
            @PathVariable Long id) {

        return studentService.deleteStudent(id);
    }
}