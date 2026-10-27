package com.rohit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
	Optional<Faculty> findByUserId(long userID);
	
	List<Faculty> findByDepartmentId(Long departmentId);


}
