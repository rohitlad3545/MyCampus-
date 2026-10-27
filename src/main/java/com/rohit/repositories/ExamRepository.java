package com.rohit.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.entites.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByDateGreaterThanEqualOrderByDateAsc(
            LocalDate date
    );
}