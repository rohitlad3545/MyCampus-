package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Course;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.CourseService;

@RestController
@RequestMapping("/api/v1/admin/course")
public class CourseController {

    @Autowired
    CourseService courseService;


    // GET ALL COURSES

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllCourses() {

        return courseService.getAllCourses();
    }


    // GET COURSE BY ID

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCourseById(
            @PathVariable Long id) {

        return courseService.getCourseById(id);
    }


    // ADD COURSE

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addCourse(
            @RequestBody Course course) {

        return courseService.addCourse(course);
    }


    // UPDATE COURSE

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateCourse(
            @PathVariable Long id,
            @RequestBody Course course) {

        return courseService.updateCourse(
                id,
                course
        );
    }


    // DELETE COURSE

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteCourse(
            @PathVariable Long id) {

        return courseService.deleteCourse(id);
    }

}