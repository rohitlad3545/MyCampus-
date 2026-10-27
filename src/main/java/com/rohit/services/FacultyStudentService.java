
package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Faculty;
import com.rohit.entites.Student;
import com.rohit.repositories.FacultyRepository;
import com.rohit.repositories.StudentRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class FacultyStudentService {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    Response response;

    public ResponseEntity<CustomResponse> getStudentsByFaculty(
            Long facultyId) {

        Optional<Faculty> faculty =
                facultyRepository.findById(facultyId);

        if (faculty.isEmpty()) {
            return response.send(
                    "Faculty not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        if (faculty.get().getDepartment() == null) {
            return response.send(
                    "No department assigned to this faculty member",
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }

        Long departmentId = faculty.get().getDepartment().getId();

        List<Student> students =
                studentRepository.findByDepartmentId(departmentId);

        return response.send(
                "Students fetched successfully",
                students,
                HttpStatus.OK
        );
    }
}
