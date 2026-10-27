package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.HOD;
import com.rohit.entites.Student;
import com.rohit.repositories.HODRepository;
import com.rohit.repositories.StudentRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class HODStudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    HODRepository hodRepository;

    @Autowired
    Response response;


    public ResponseEntity<CustomResponse> getStudentsByHOD(
            long id) {


        System.out.println(
                "HOD SERVICE - HOD ID = " + id
        );


        Optional<HOD> hodOptional =
                hodRepository.findById(id);


        if (hodOptional.isEmpty()) {

            return response.send(
                    "HOD profile not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        HOD hod =
                hodOptional.get();


        System.out.println(
                "HOD FOUND = " + hod.getId()
        );


        if (hod.getDepartment() == null) {

            return response.send(
                    "Department not assigned to HOD",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        long departmentId =
                hod.getDepartment().getId();


        System.out.println(
                "HOD DEPARTMENT ID = "
                        + departmentId
        );


        List<Student> students =
                studentRepository
                        .findByDepartmentId(
                                departmentId
                        );


        System.out.println(
                "STUDENTS FOUND = "
                        + students.size()
        );


        return response.send(
                "Department students fetched successfully",
                students,
                HttpStatus.OK
        );
    }
}