package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Faculty;
import com.rohit.repositories.FacultyRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class FacultyService {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    Response response;


    // GET ALL FACULTY

    public ResponseEntity<CustomResponse> getAllFaculty() {

        List<Faculty> faculty =
                facultyRepository.findAll();

        return response.send(
                "Faculty fetched successfully",
                faculty,
                HttpStatus.OK
        );
    }


    // GET FACULTY BY ID

    public ResponseEntity<CustomResponse> getFacultyById(Long id) {

        Optional<Faculty> faculty =
                facultyRepository.findById(id);

        if (faculty.isEmpty()) {

            return response.send(
                    "Faculty not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        return response.send(
                "Faculty found",
                faculty.get(),
                HttpStatus.OK
        );
    }


    // ADD FACULTY

    public ResponseEntity<CustomResponse> addFaculty(
            Faculty faculty) {

        Faculty savedFaculty =
                facultyRepository.save(faculty);

        return response.send(
                "Faculty added successfully",
                savedFaculty,
                HttpStatus.OK
        );
    }


    // UPDATE FACULTY

    public ResponseEntity<CustomResponse> updateFaculty(
            Long id,
            Faculty faculty) {

        Optional<Faculty> existingFaculty =
                facultyRepository.findById(id);

        if (existingFaculty.isEmpty()) {

            return response.send(
                    "Faculty not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Faculty existing =
                existingFaculty.get();

        existing.setName(faculty.getName());
        existing.setEmployeeId(faculty.getEmployeeId());
        existing.setPhone(faculty.getPhone());
        existing.setEmail(faculty.getEmail());
        existing.setDesignation(faculty.getDesignation());
        existing.setDepartment(faculty.getDepartment());

        Faculty updatedFaculty =
                facultyRepository.save(existing);

        return response.send(
                "Faculty updated successfully",
                updatedFaculty,
                HttpStatus.OK
        );
    }


    // DELETE FACULTY

    public ResponseEntity<CustomResponse> deleteFaculty(
            Long id) {

        Optional<Faculty> faculty =
                facultyRepository.findById(id);

        if (faculty.isEmpty()) {

            return response.send(
                    "Faculty not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        facultyRepository.deleteById(id);

        return response.send(
                "Faculty deleted successfully",
                null,
                HttpStatus.OK
        );
    }

}