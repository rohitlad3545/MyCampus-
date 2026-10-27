package com.rohit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.entites.Course;

public interface CourseRepository
        extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

}