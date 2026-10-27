package com.rohit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.AcademicYear;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
	

}
