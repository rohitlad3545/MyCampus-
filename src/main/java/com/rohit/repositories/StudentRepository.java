
package com.rohit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUserId(long userID);

    List<Student> findByDepartmentId(Long departmentId);

    Optional<Student> findByUserUsername(String username);
}
