
package com.rohit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.Timetable;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.TimetableService;

@RestController
@RequestMapping("/api/v1/hod/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllTimetables() {
        return timetableService.getAllTimetables();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getTimetableById(
            @PathVariable long id) {
        return timetableService.getTimetableById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addTimetable(
            @RequestBody Timetable timetable) {
        return timetableService.addTimetable(timetable);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateTimetable(
            @PathVariable long id,
            @RequestBody Timetable timetable) {
        return timetableService.updateTimetable(id, timetable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteTimetable(
            @PathVariable long id) {
        return timetableService.deleteTimetable(id);
    }
}
