
package com.rohit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Timetable;

@Repository
public interface TimetableRepository
        extends JpaRepository<Timetable, Long> {

    List<Timetable> findByCourseDepartmentId(long departmentId);
}
