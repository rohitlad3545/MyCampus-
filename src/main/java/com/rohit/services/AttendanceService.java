package com.rohit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Attendance;
import com.rohit.entites.Faculty;
import com.rohit.entites.Student;
import com.rohit.entites.Subject;
import com.rohit.repositories.AttendanceRepository;
import com.rohit.repositories.FacultyRepository;
import com.rohit.repositories.StudentRepository;
import com.rohit.repositories.SubjectRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    Response response;

    public ResponseEntity<CustomResponse> getAllAttendance() {

        List<Attendance> attendanceList =
                attendanceRepository.findAll();

        return response.send(
                "Attendance fetched successfully",
                attendanceList,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> getAttendanceById(long id) {

        Optional<Attendance> attendance =
                attendanceRepository.findById(id);

        if (attendance.isPresent()) {

            return response.send(
                    "Attendance found",
                    attendance.get(),
                    HttpStatus.OK
            );
        }

        return response.send(
                "Attendance not found",
                null,
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<CustomResponse> addAttendance(
            Attendance attendance) {

        if (attendance.getStudent() == null ||
                attendance.getStudent().getId() == 0) {

            return response.send(
                    "Student is required",
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (attendance.getSubject() == null ||
                attendance.getSubject().getId() == 0) {

            return response.send(
                    "Subject is required",
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<Student> student =
                studentRepository.findById(
                        attendance.getStudent().getId()
                );

        if (student.isEmpty()) {

            return response.send(
                    "Student not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Optional<Subject> subject =
                subjectRepository.findById(
                        attendance.getSubject().getId()
                );

        if (subject.isEmpty()) {

            return response.send(
                    "Subject not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        attendance.setStudent(student.get());
        attendance.setSubject(subject.get());

        if (attendance.getFaculty() != null &&
                attendance.getFaculty().getId() != 0) {

            Optional<Faculty> faculty =
                    facultyRepository.findById(
                            attendance.getFaculty().getId()
                    );

            if (faculty.isEmpty()) {

                return response.send(
                        "Faculty not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            attendance.setFaculty(faculty.get());
        }

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return response.send(
                "Attendance added successfully",
                savedAttendance,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<CustomResponse> updateAttendance(
            long id,
            Attendance attendance) {

        Optional<Attendance> existingAttendance =
                attendanceRepository.findById(id);

        if (existingAttendance.isEmpty()) {

            return response.send(
                    "Attendance not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Attendance existing =
                existingAttendance.get();

        if (attendance.getStudent() != null &&
                attendance.getStudent().getId() != 0) {

            Optional<Student> student =
                    studentRepository.findById(
                            attendance.getStudent().getId()
                    );

            if (student.isEmpty()) {

                return response.send(
                        "Student not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }

            existing.setStudent(student.get());
        }

        if (attendance.getSubject() != null &&
                attendance.getSubject().getId() != 0) {

            Optional<Subject> subject =
                    subjectRepository.findById(
                            attendance.getSubject().getId()
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

        if (attendance.getFaculty() != null &&
                attendance.getFaculty().getId() != 0) {

            Optional<Faculty> faculty =
                    facultyRepository.findById(
                            attendance.getFaculty().getId()
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

        existing.setDate(attendance.getDate());
        existing.setStatus(attendance.getStatus());

        Attendance updatedAttendance =
                attendanceRepository.save(existing);

        return response.send(
                "Attendance updated successfully",
                updatedAttendance,
                HttpStatus.OK
        );
    }

    public ResponseEntity<CustomResponse> deleteAttendance(
            long id) {

        Optional<Attendance> attendance =
                attendanceRepository.findById(id);

        if (attendance.isEmpty()) {

            return response.send(
                    "Attendance not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        attendanceRepository.deleteById(id);

        return response.send(
                "Attendance deleted successfully",
                null,
                HttpStatus.OK
        );
    }
    
    public ResponseEntity<CustomResponse> getAttendanceByFaculty(
            long facultyId) {

        List<Attendance> attendanceList =
                attendanceRepository.findByFacultyId(facultyId);

        return response.send(
                "Faculty attendance fetched successfully",
                attendanceList,
                HttpStatus.OK
        );
    }
    
    public ResponseEntity<CustomResponse> getAttendanceByStudentId(
            long studentId) {

        Optional<Student> student =
                studentRepository.findById(studentId);

        if (student.isEmpty()) {
            return response.send(
                    "Student not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        List<Attendance> attendanceList =
                attendanceRepository.findByStudentId(studentId);

        return response.send(
                "Student attendance fetched successfully",
                attendanceList,
                HttpStatus.OK
        );
    }
}