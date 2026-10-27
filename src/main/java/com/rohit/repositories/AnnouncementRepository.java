
package com.rohit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entites.Announcement;

@Repository
public interface AnnouncementRepository
        extends JpaRepository<Announcement, Long> {

    List<Announcement> findByDepartment_IdOrderByCreatedAtDesc(
            long departmentId);

    Optional<Announcement> findByIdAndDepartment_Id(
            long id, long departmentId);
}
