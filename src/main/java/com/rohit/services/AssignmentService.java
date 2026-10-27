package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Assignment;
import com.rohit.entites.Faculty;
import com.rohit.entites.Subject;
import com.rohit.repositories.AssignmentRepository;
import com.rohit.repositories.FacultyRepository;
import com.rohit.repositories.SubjectRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    Response response;

    public ResponseEntity<CustomResponse> getAllAssignments() {

        List<Assignment> assignments =
                assignmentRepository.findAll();

        return response.send(
                "Assignments fetched successfully",
                assignments,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> getAssignmentById(
            long id) {

        Optional<Assignment> assignment =
                assignmentRepository.findById(id);

        if (assignment.isPresent()) {

            return response.send(
                    "Assignment found",
                    assignment.get(),
                    HttpStatus.OK
            );
        }

        return response.send(
                "Assignment not found",
                null,
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<CustomResponse> addAssignment(
            Assignment assignment) {

        if (assignment.getSubject() != null &&
                assignment.getSubject().getId() != 0) {

            Optional<Subject> subject =
                    subjectRepository.findById(
                            assignment.getSubject().getId()
                    );

            if (subject.isEmpty()) {

                return response.send(
                        "Subject not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            assignment.setSubject(subject.get());
        }

        if (assignment.getFaculty() != null &&
                assignment.getFaculty().getId() != 0) {

            Optional<Faculty> faculty =
                    facultyRepository.findById(
                            assignment.getFaculty().getId()
                    );

            if (faculty.isEmpty()) {

                return response.send(
                        "Faculty not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            assignment.setFaculty(faculty.get());
        }

        Assignment savedAssignment =
                assignmentRepository.save(assignment);

        return response.send(
                "Assignment added successfully",
                savedAssignment,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<CustomResponse> updateAssignment(
            long id,
            Assignment assignment) {

        Optional<Assignment> existingAssignment =
                assignmentRepository.findById(id);

        if (existingAssignment.isEmpty()) {

            return response.send(
                    "Assignment not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Assignment existing =
                existingAssignment.get();

        existing.setTitle(assignment.getTitle());
        existing.setDescription(assignment.getDescription());
        existing.setDueDate(assignment.getDueDate());

        if (assignment.getSubject() != null &&
                assignment.getSubject().getId() != 0) {

            Optional<Subject> subject =
                    subjectRepository.findById(
                            assignment.getSubject().getId()
                    );

            if (subject.isEmpty()) {

                return response.send(
                        "Subject not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            existing.setSubject(subject.get());
        }

        if (assignment.getFaculty() != null &&
                assignment.getFaculty().getId() != 0) {

            Optional<Faculty> faculty =
                    facultyRepository.findById(
                            assignment.getFaculty().getId()
                    );

            if (faculty.isEmpty()) {

                return response.send(
                        "Faculty not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            existing.setFaculty(faculty.get());
        }

        Assignment updatedAssignment =
                assignmentRepository.save(existing);

        return response.send(
                "Assignment updated successfully",
                updatedAssignment,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> deleteAssignment(
            long id) {

        Optional<Assignment> assignment =
                assignmentRepository.findById(id);

        if (assignment.isEmpty()) {

            return response.send(
                    "Assignment not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        assignmentRepository.deleteById(id);

        return response.send(
                "Assignment deleted successfully",
                null,
                HttpStatus.OK
        );
    }
}