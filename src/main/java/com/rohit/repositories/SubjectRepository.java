package com.rohit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>  {
	Optional<Subject> findByCode(String code);
	

    List<Subject> findByCourseDepartmentId(Long departmentId);

    List<Subject> findByFacultyId(Long facultyId);

}
