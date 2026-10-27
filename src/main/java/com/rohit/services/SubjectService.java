
package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Course;
import com.rohit.entites.Faculty;
import com.rohit.entites.Semester;
import com.rohit.entites.Subject;
import com.rohit.repositories.CourseRepository;
import com.rohit.repositories.FacultyRepository;
import com.rohit.repositories.SemesterRepository;
import com.rohit.repositories.SubjectRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    Response response;

    // GET ALL SUBJECTS
    public ResponseEntity<CustomResponse> getAllSubjects() {

        List<Subject> subjects = subjectRepository.findAll();

        return response.send(
                "Subjects fetched successfully",
                subjects,
                HttpStatus.OK
        );
    }

    // GET SUBJECT BY ID
    public ResponseEntity<CustomResponse> getSubjectById(long id) {

        Optional<Subject> subject = subjectRepository.findById(id);

        if (subject.isPresent()) {
            return response.send(
                    "Subject found",
                    subject.get(),
                    HttpStatus.OK
            );
        }

        return response.send(
                "Subject not found",
                null,
                HttpStatus.NOT_FOUND
        );
    }

    // GET SUBJECTS BY FACULTY ID
    public ResponseEntity<CustomResponse> getSubjectsByFaculty(
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

        List<Subject> subjects =
                subjectRepository.findByFacultyId(facultyId);

        return response.send(
                "Faculty subjects fetched successfully",
                subjects,
                HttpStatus.OK
        );
    }

    // ADD SUBJECT
    public ResponseEntity<CustomResponse> addSubject(
            Subject subject) {

        // Resolve Course
        if (subject.getCourse() != null
                && subject.getCourse().getId() != 0) {

            Optional<Course> course =
                    courseRepository.findById(
                            subject.getCourse().getId()
                    );

            if (course.isEmpty()) {
                return response.send(
                        "Course not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            subject.setCourse(course.get());
        }

        // Resolve Semester
        if (subject.getSemester() != null
                && subject.getSemester().getId() != 0) {

            Optional<Semester> semester =
                    semesterRepository.findById(
                            subject.getSemester().getId()
                    );

            if (semester.isEmpty()) {
                return response.send(
                        "Semester not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            subject.setSemester(semester.get());
        }

        // Resolve Faculty
        if (subject.getFaculty() != null
                && subject.getFaculty().getId() != 0) {

            Optional<Faculty> faculty =
                    facultyRepository.findById(
                            subject.getFaculty().getId()
                    );

            if (faculty.isEmpty()) {
                return response.send(
                        "Faculty not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            subject.setFaculty(faculty.get());

        } else {
            subject.setFaculty(null);
        }

        // Save Subject
        Subject savedSubject = subjectRepository.save(subject);

        return response.send(
                "Subject added successfully",
                savedSubject,
                HttpStatus.CREATED
        );
    }

    // UPDATE SUBJECT
    public ResponseEntity<CustomResponse> updateSubject(
            long id,
            Subject subject) {

        Optional<Subject> existingSubject =
                subjectRepository.findById(id);

        if (existingSubject.isEmpty()) {
            return response.send(
                    "Subject not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Subject existing = existingSubject.get();

        // Update basic details
        existing.setName(subject.getName());
        existing.setCode(subject.getCode());
        existing.setCredits(subject.getCredits());

        // Update Course
        if (subject.getCourse() != null
                && subject.getCourse().getId() != 0) {

            Optional<Course> course =
                    courseRepository.findById(
                            subject.getCourse().getId()
                    );

            if (course.isEmpty()) {
                return response.send(
                        "Course not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            existing.setCourse(course.get());
        }

        // Update Semester
        if (subject.getSemester() != null
                && subject.getSemester().getId() != 0) {

            Optional<Semester> semester =
                    semesterRepository.findById(
                            subject.getSemester().getId()
                    );

            if (semester.isEmpty()) {
                return response.send(
                        "Semester not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            existing.setSemester(semester.get());
        }

        // Update Faculty Assignment
        if (subject.getFaculty() != null
                && subject.getFaculty().getId() != 0) {

            Optional<Faculty> faculty =
                    facultyRepository.findById(
                            subject.getFaculty().getId()
                    );

            if (faculty.isEmpty()) {
                return response.send(
                        "Faculty not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            existing.setFaculty(faculty.get());

        } else {
            existing.setFaculty(null);
        }

        // Save Updated Subject
        Subject updatedSubject = subjectRepository.save(existing);

        return response.send(
                "Subject updated successfully",
                updatedSubject,
                HttpStatus.OK
        );
    }

    // DELETE SUBJECT
    public ResponseEntity<CustomResponse> deleteSubject(long id) {

        Optional<Subject> subject = subjectRepository.findById(id);

        if (subject.isEmpty()) {
            return response.send(
                    "Subject not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        subjectRepository.deleteById(id);

        return response.send(
                "Subject deleted successfully",
                null,
                HttpStatus.OK
        );
    }
}
