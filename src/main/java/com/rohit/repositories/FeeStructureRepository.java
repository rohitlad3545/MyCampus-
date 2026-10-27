package com.rohit.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rohit.entites.FeeStructure;

@Repository
public interface FeeStructureRepository
        extends JpaRepository<FeeStructure, Long> {

    List<FeeStructure> findByCourseId(Long courseId);

    List<FeeStructure> findBySemesterId(Long semesterId);
}