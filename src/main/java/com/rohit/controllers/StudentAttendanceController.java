package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.AttendanceService;

@RestController
@RequestMapping("/api/v1/student/attendance")
public class StudentAttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/{studentId}")
    public ResponseEntity<CustomResponse> getStudentAttendance(
            @PathVariable Long studentId) {

        return attendanceService.getAttendanceByStudentId(studentId);
    }
}