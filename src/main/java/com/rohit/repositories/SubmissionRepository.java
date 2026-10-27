package com.rohit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}
