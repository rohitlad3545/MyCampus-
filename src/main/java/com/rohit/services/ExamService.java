package com.rohit.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Exam;
import com.rohit.repositories.ExamRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    Response response;


    // GET ALL EXAMS

    public ResponseEntity<CustomResponse> getAllExams() {

        List<Exam> exams =
                examRepository.findAll();

        return response.send(
                "Exams fetched successfully",
                exams,
                HttpStatus.OK
        );
    }


    // GET EXAM BY ID

    public ResponseEntity<CustomResponse> getExamById(
            Long id) {

        Optional<Exam> exam =
                examRepository.findById(id);

        if (exam.isEmpty()) {

            return response.send(
                    "Exam not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        return response.send(
                "Exam found",
                exam.get(),
                HttpStatus.OK
        );
    }


    // ADD EXAM

    public ResponseEntity<CustomResponse> addExam(
            Exam exam) {

        Exam savedExam =
                examRepository.save(exam);

        return response.send(
                "Exam added successfully",
                savedExam,
                HttpStatus.OK
        );
    }


    // UPDATE EXAM

    public ResponseEntity<CustomResponse> updateExam(
            Long id,
            Exam exam) {

        Optional<Exam> existingExam =
                examRepository.findById(id);

        if (existingExam.isEmpty()) {

            return response.send(
                    "Exam not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Exam existing =
                existingExam.get();


        existing.setName(
                exam.getName()
        );

        existing.setExamType(
                exam.getExamType()
        );

        existing.setDate(
                exam.getDate()
        );

        existing.setSemester(
                exam.getSemester()
        );


        Exam updatedExam =
                examRepository.save(existing);


        return response.send(
                "Exam updated successfully",
                updatedExam,
                HttpStatus.OK
        );
    }


    // DELETE EXAM

    public ResponseEntity<CustomResponse> deleteExam(
            Long id) {

        Optional<Exam> exam =
                examRepository.findById(id);

        if (exam.isEmpty()) {

            return response.send(
                    "Exam not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        examRepository.deleteById(id);

        return response.send(
                "Exam deleted successfully",
                null,
                HttpStatus.OK
        );
    }
    
    public ResponseEntity<CustomResponse> getUpcomingExams() {

        List<Exam> exams =
                examRepository
                        .findByDateGreaterThanEqualOrderByDateAsc(
                                LocalDate.now()
                        );

        return response.send(
                "Upcoming exams fetched successfully",
                exams,
                HttpStatus.OK
        );
    }

}