package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Course;
import com.rohit.repositories.CourseRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    Response response;


    // GET ALL COURSES

    public ResponseEntity<CustomResponse> getAllCourses() {

        List<Course> courses =
                courseRepository.findAll();

        return response.send(
                "Courses fetched successfully",
                courses,
                HttpStatus.OK
        );
    }


    // GET COURSE BY ID

    public ResponseEntity<CustomResponse> getCourseById(
            Long id) {

        Optional<Course> course =
                courseRepository.findById(id);

        if (course.isEmpty()) {

            return response.send(
                    "Course not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        return response.send(
                "Course found",
                course.get(),
                HttpStatus.OK
        );
    }


    // ADD COURSE

    public ResponseEntity<CustomResponse> addCourse(
            Course course) {

        Course savedCourse =
                courseRepository.save(course);

        return response.send(
                "Course added successfully",
                savedCourse,
                HttpStatus.OK
        );
    }


    // UPDATE COURSE

    public ResponseEntity<CustomResponse> updateCourse(
            Long id,
            Course course) {

        Optional<Course> existingCourse =
                courseRepository.findById(id);

        if (existingCourse.isEmpty()) {

            return response.send(
                    "Course not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Course existing =
                existingCourse.get();


        existing.setName(
                course.getName()
        );

        existing.setCode(
                course.getCode()
        );

        existing.setDuration(
                course.getDuration()
        );

        existing.setDepartment(
                course.getDepartment()
        );


        Course updatedCourse =
                courseRepository.save(existing);


        return response.send(
                "Course updated successfully",
                updatedCourse,
                HttpStatus.OK
        );
    }


    // DELETE COURSE

    public ResponseEntity<CustomResponse> deleteCourse(
            Long id) {

        Optional<Course> course =
                courseRepository.findById(id);

        if (course.isEmpty()) {

            return response.send(
                    "Course not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        courseRepository.deleteById(id);

        return response.send(
                "Course deleted successfully",
                null,
                HttpStatus.OK
        );
    }

}