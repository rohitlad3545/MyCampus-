package com.rohit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

}
