
package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Timetable;
import com.rohit.repositories.TimetableRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;
    private final Response response;

    public TimetableService(
            TimetableRepository timetableRepository,
            Response response) {
        this.timetableRepository = timetableRepository;
        this.response = response;
    }

    public ResponseEntity<CustomResponse> getAllTimetables() {
        List<Timetable> timetables = timetableRepository.findAll();

        return response.send(
                "Timetable records fetched successfully",
                timetables,
                HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> getTimetableById(long id) {
        Optional<Timetable> optional =
                timetableRepository.findById(id);

        if (optional.isEmpty()) {
            return response.send(
                    "Timetable record not found",
                    null,
                    HttpStatus.NOT_FOUND);
        }

        return response.send(
                "Timetable record fetched successfully",
                optional.get(),
                HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> addTimetable(
            Timetable timetable) {

        if (!isValid(timetable)) {
            return response.send(
                    "Please provide course, semester, subject, faculty, day, start time and end time",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        if (!timetable.getEndTime().isAfter(timetable.getStartTime())) {
            return response.send(
                    "End time must be later than start time",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        Timetable saved = timetableRepository.save(timetable);

        return response.send(
                "Timetable created successfully",
                saved,
                HttpStatus.CREATED);
    }

    public ResponseEntity<CustomResponse> updateTimetable(
            long id,
            Timetable timetable) {

        Optional<Timetable> optional =
                timetableRepository.findById(id);

        if (optional.isEmpty()) {
            return response.send(
                    "Timetable record not found",
                    null,
                    HttpStatus.NOT_FOUND);
        }

        if (!isValid(timetable)) {
            return response.send(
                    "Please provide course, semester, subject, faculty, day, start time and end time",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        if (!timetable.getEndTime().isAfter(timetable.getStartTime())) {
            return response.send(
                    "End time must be later than start time",
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        Timetable existing = optional.get();

        existing.setCourse(timetable.getCourse());
        existing.setSemester(timetable.getSemester());
        existing.setSubject(timetable.getSubject());
        existing.setFaculty(timetable.getFaculty());
        existing.setDay(timetable.getDay().trim());
        existing.setStartTime(timetable.getStartTime());
        existing.setEndTime(timetable.getEndTime());

        Timetable updated = timetableRepository.save(existing);

        return response.send(
                "Timetable updated successfully",
                updated,
                HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> deleteTimetable(long id) {
        Optional<Timetable> optional =
                timetableRepository.findById(id);

        if (optional.isEmpty()) {
            return response.send(
                    "Timetable record not found",
                    null,
                    HttpStatus.NOT_FOUND);
        }

        timetableRepository.delete(optional.get());

        return response.send(
                "Timetable deleted successfully",
                null,
                HttpStatus.OK);
    }

    private boolean isValid(Timetable timetable) {
        return timetable != null
                && timetable.getCourse() != null
                && timetable.getSemester() != null
                && timetable.getSubject() != null
                && timetable.getFaculty() != null
                && timetable.getDay() != null
                && !timetable.getDay().isBlank()
                && timetable.getStartTime() != null
                && timetable.getEndTime() != null;
    }
}
