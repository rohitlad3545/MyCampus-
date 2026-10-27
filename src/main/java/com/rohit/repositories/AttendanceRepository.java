package com.rohit.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Attendance;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByFacultyId(long facultyId);

    List<Attendance> findByStudentId(long studentId);
}