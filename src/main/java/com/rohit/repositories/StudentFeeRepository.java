
package com.rohit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rohit.entites.StudentFee;

import jakarta.persistence.LockModeType;

@Repository
public interface StudentFeeRepository
        extends JpaRepository<StudentFee, Long> {

    List<StudentFee> findByStudentId(Long studentId);

    List<StudentFee> findByFeeStructureId(Long feeStructureId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sf FROM StudentFee sf WHERE sf.id = :id")
    Optional<StudentFee> findByIdForUpdate(
            @Param("id") Long id);
}
