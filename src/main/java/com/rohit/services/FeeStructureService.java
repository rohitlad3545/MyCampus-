package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.AcademicYear;
import com.rohit.entites.Course;
import com.rohit.entites.FeeStructure;
import com.rohit.entites.Semester;
import com.rohit.repositories.AcademicYearRepository;
import com.rohit.repositories.CourseRepository;
import com.rohit.repositories.FeeStructureRepository;
import com.rohit.repositories.SemesterRepository;
import com.rohit.response_wrapper.CustomResponse;

@Service
public class FeeStructureService {

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private AcademicYearRepository academicYearRepository;

    private ResponseEntity<CustomResponse> response(
            String message, Object data, HttpStatus status) {

        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage(message);
        customResponse.setData(data);

        return new ResponseEntity<>(customResponse, status);
    }

    public ResponseEntity<CustomResponse> getAll() {
        List<FeeStructure> fees = feeStructureRepository.findAll();
        return response("Fee structures fetched successfully",
                fees, HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> getById(Long id) {
        Optional<FeeStructure> fee =
                feeStructureRepository.findById(id);

        if (fee.isEmpty()) {
            return response("Fee structure not found",
                    null, HttpStatus.NOT_FOUND);
        }

        return response("Fee structure fetched successfully",
                fee.get(), HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> add(FeeStructure request) {

        if (request.getTotalAmount() < 0) {
            return response("Fee amount cannot be negative",
                    null, HttpStatus.BAD_REQUEST);
        }

        if (request.getCourse() == null
                || request.getSemester() == null) {
            return response("Course and semester are required",
                    null, HttpStatus.BAD_REQUEST);
        }

        Optional<Course> course = courseRepository.findById(
                request.getCourse().getId());

        Optional<Semester> semester = semesterRepository.findById(
                request.getSemester().getId());

        if (course.isEmpty() || semester.isEmpty()) {
            return response("Invalid course or semester",
                    null, HttpStatus.BAD_REQUEST);
        }

        request.setCourse(course.get());
        request.setSemester(semester.get());

        if (request.getAcademicYear() != null) {
            Optional<AcademicYear> year =
                    academicYearRepository.findById(
                            request.getAcademicYear().getId());

            if (year.isEmpty()) {
                return response("Academic year not found",
                        null, HttpStatus.BAD_REQUEST);
            }

            request.setAcademicYear(year.get());
        }

        FeeStructure saved =
                feeStructureRepository.save(request);

        return response("Fee structure added successfully",
                saved, HttpStatus.CREATED);
    }

    public ResponseEntity<CustomResponse> update(
            Long id, FeeStructure request) {

        Optional<FeeStructure> existing =
                feeStructureRepository.findById(id);

        if (existing.isEmpty()) {
            return response("Fee structure not found",
                    null, HttpStatus.NOT_FOUND);
        }

        if (request.getTotalAmount() < 0) {
            return response("Fee amount cannot be negative",
                    null, HttpStatus.BAD_REQUEST);
        }

        if (request.getCourse() == null
                || request.getSemester() == null) {
            return response("Course and semester are required",
                    null, HttpStatus.BAD_REQUEST);
        }

        Optional<Course> course = courseRepository.findById(
                request.getCourse().getId());

        Optional<Semester> semester = semesterRepository.findById(
                request.getSemester().getId());

        if (course.isEmpty() || semester.isEmpty()) {
            return response("Invalid course or semester",
                    null, HttpStatus.BAD_REQUEST);
        }

        FeeStructure fee = existing.get();
        fee.setCourse(course.get());
        fee.setSemester(semester.get());
        fee.setTotalAmount(request.getTotalAmount());

        if (request.getAcademicYear() == null) {
            fee.setAcademicYear(null);
        } else {
            Optional<AcademicYear> year =
                    academicYearRepository.findById(
                            request.getAcademicYear().getId());

            if (year.isEmpty()) {
                return response("Academic year not found",
                        null, HttpStatus.BAD_REQUEST);
            }

            fee.setAcademicYear(year.get());
        }

        FeeStructure saved = feeStructureRepository.save(fee);

        return response("Fee structure updated successfully",
                saved, HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> delete(Long id) {
        if (!feeStructureRepository.existsById(id)) {
            return response("Fee structure not found",
                    null, HttpStatus.NOT_FOUND);
        }

        feeStructureRepository.deleteById(id);

        return response("Fee structure deleted successfully",
                null, HttpStatus.OK);
    }
}
