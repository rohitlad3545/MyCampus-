package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Student;
import com.rohit.repositories.StudentRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    Response response;


    // GET ALL STUDENTS

    public ResponseEntity<CustomResponse> getAllStudents() {

        List<Student> students =
                studentRepository.findAll();

        return response.send(
                "Students fetched successfully",
                students,
                HttpStatus.OK
        );
    }


    // GET STUDENT BY ID

    public ResponseEntity<CustomResponse> getStudentById(
            Long id) {

        Optional<Student> student =
                studentRepository.findById(id);

        if (student.isEmpty()) {

            return response.send(
                    "Student not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        return response.send(
                "Student found",
                student.get(),
                HttpStatus.OK
        );
    }


    // ADD STUDENT

    public ResponseEntity<CustomResponse> addStudent(
            Student student) {

        Student savedStudent =
                studentRepository.save(student);

        return response.send(
                "Student added successfully",
                savedStudent,
                HttpStatus.OK
        );
    }


    // UPDATE STUDENT

    public ResponseEntity<CustomResponse> updateStudent(
            Long id,
            Student student) {

        Optional<Student> existingStudent =
                studentRepository.findById(id);

        if (existingStudent.isEmpty()) {

            return response.send(
                    "Student not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        Student existing =
                existingStudent.get();


        existing.setName(student.getName());

        existing.setRollNo(student.getRollNo());

        existing.setPhone(student.getPhone());

        existing.setEmail(student.getEmail());

        existing.setAddress(student.getAddress());

        existing.setDepartment(
                student.getDepartment()
        );

        existing.setCourse(
                student.getCourse()
        );

        existing.setSemester(
                student.getSemester()
        );


        Student updatedStudent =
                studentRepository.save(existing);


        return response.send(
                "Student updated successfully",
                updatedStudent,
                HttpStatus.OK
        );
    }


    // DELETE STUDENT

    public ResponseEntity<CustomResponse> deleteStudent(
            Long id) {

        Optional<Student> student =
                studentRepository.findById(id);

        if (student.isEmpty()) {

            return response.send(
                    "Student not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        studentRepository.deleteById(id);


        return response.send(
                "Student deleted successfully",
                null,
                HttpStatus.OK
        );
    }
}