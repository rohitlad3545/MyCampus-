package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Semester;
import com.rohit.repositories.SemesterRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class SemesterService {

    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    Response response;

    public ResponseEntity<CustomResponse> getAllSemesters() {

        List<Semester> semesters =
                semesterRepository.findAll();

        return response.send(
                "Semesters fetched successfully",
                semesters,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> getSemesterById(
            long id) {

        Optional<Semester> semester =
                semesterRepository.findById(id);

        if (semester.isPresent()) {

            return response.send(
                    "Semester found",
                    semester.get(),
                    HttpStatus.OK
            );
        }

        return response.send(
                "Semester not found",
                null,
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<CustomResponse> addSemester(
            Semester semester) {

        Semester savedSemester =
                semesterRepository.save(semester);

        return response.send(
                "Semester added successfully",
                savedSemester,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<CustomResponse> updateSemester(
            long id,
            Semester semester) {

        Optional<Semester> existingSemester =
                semesterRepository.findById(id);

        if (existingSemester.isEmpty()) {

            return response.send(
                    "Semester not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Semester existing =
                existingSemester.get();

        existing.setSemesterNumber(
                semester.getSemesterNumber()
        );

        existing.setAcademicYear(
                semester.getAcademicYear()
        );

        Semester updatedSemester =
                semesterRepository.save(existing);

        return response.send(
                "Semester updated successfully",
                updatedSemester,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> deleteSemester(
            long id) {

        Optional<Semester> semester =
                semesterRepository.findById(id);

        if (semester.isEmpty()) {

            return response.send(
                    "Semester not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        semesterRepository.deleteById(id);

        return response.send(
                "Semester deleted successfully",
                null,
                HttpStatus.OK
        );
    }
}