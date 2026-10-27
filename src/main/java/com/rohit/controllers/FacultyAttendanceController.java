package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.entites.Attendance;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.AttendanceService;

@RestController
@RequestMapping("/api/v1/faculty/attendance")
public class FacultyAttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/all/{facultyId}")
    public ResponseEntity<CustomResponse> getFacultyAttendance(
            @PathVariable long facultyId) {

        return attendanceService.getAttendanceByFaculty(
                facultyId
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addAttendance(
            @RequestBody Attendance attendance) {

        return attendanceService.addAttendance(
                attendance
        );
    }
}