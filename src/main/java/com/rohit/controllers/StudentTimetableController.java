
package com.rohit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.TimetableService;

@RestController
@RequestMapping("/api/v1/student/timetable")
public class StudentTimetableController {

    private final TimetableService timetableService;

    public StudentTimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getStudentTimetable() {
        return timetableService.getAllTimetables();
    }
}
