package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.AcademicYear;
import com.rohit.repositories.AcademicYearRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class AcademicYearService {

    @Autowired
    AcademicYearRepository academicYearRepository;

    @Autowired
    Response response;

    public ResponseEntity<CustomResponse> getAllAcademicYears() {

        List<AcademicYear> academicYears =
                academicYearRepository.findAll();

        return response.send(
                "Academic years fetched successfully",
                academicYears,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> getAcademicYearById(
            long id) {

        Optional<AcademicYear> academicYear =
                academicYearRepository.findById(id);

        if (academicYear.isPresent()) {

            return response.send(
                    "Academic year found",
                    academicYear.get(),
                    HttpStatus.OK
            );
        }

        return response.send(
                "Academic year not found",
                null,
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<CustomResponse> addAcademicYear(
            AcademicYear academicYear) {

        AcademicYear savedAcademicYear =
                academicYearRepository.save(academicYear);

        return response.send(
                "Academic year added successfully",
                savedAcademicYear,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<CustomResponse> updateAcademicYear(
            long id,
            AcademicYear academicYear) {

        Optional<AcademicYear> existingAcademicYear =
                academicYearRepository.findById(id);

        if (existingAcademicYear.isEmpty()) {

            return response.send(
                    "Academic year not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        AcademicYear existing =
                existingAcademicYear.get();

        existing.setYear(academicYear.getYear());
        existing.setActive(academicYear.isActive());

        AcademicYear updatedAcademicYear =
                academicYearRepository.save(existing);

        return response.send(
                "Academic year updated successfully",
                updatedAcademicYear,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> deleteAcademicYear(
            long id) {

        Optional<AcademicYear> academicYear =
                academicYearRepository.findById(id);

        if (academicYear.isEmpty()) {

            return response.send(
                    "Academic year not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        academicYearRepository.deleteById(id);

        return response.send(
                "Academic year deleted successfully",
                null,
                HttpStatus.OK
        );
    }
}