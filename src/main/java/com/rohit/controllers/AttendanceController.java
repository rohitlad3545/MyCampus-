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

import com.rohit.entites.Attendance;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.AttendanceService;

@RestController
@RequestMapping("/api/v1/admin/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAttendanceById(
            @PathVariable long id) {

        return attendanceService.getAttendanceById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addAttendance(
            @RequestBody Attendance attendance) {

        return attendanceService.addAttendance(attendance);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateAttendance(
            @PathVariable long id,
            @RequestBody Attendance attendance) {

        return attendanceService.updateAttendance(
                id,
                attendance
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteAttendance(
            @PathVariable long id) {

        return attendanceService.deleteAttendance(id);
    }
}