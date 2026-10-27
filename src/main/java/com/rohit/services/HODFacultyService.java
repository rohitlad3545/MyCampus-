package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Faculty;
import com.rohit.entites.HOD;
import com.rohit.repositories.FacultyRepository;
import com.rohit.repositories.HODRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class HODFacultyService {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    HODRepository hodRepository;

    @Autowired
    Response response;


    public ResponseEntity<CustomResponse> getFacultyByHOD(
            long hodId) {


        Optional<HOD> hodOptional =
                hodRepository.findById(hodId);


        if (hodOptional.isEmpty()) {

            return response.send(
                    "HOD profile not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        HOD hod =
                hodOptional.get();


        if (hod.getDepartment() == null) {

            return response.send(
                    "Department not assigned to HOD",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        long departmentId =
                hod.getDepartment().getId();


        List<Faculty> facultyList =
                facultyRepository
                        .findByDepartmentId(
                                departmentId
                        );


        return response.send(
                "Department faculty fetched successfully",
                facultyList,
                HttpStatus.OK
        );
    }
}