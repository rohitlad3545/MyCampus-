
package com.rohit.services;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rohit.entites.Announcement;
import com.rohit.entites.HOD;
import com.rohit.repositories.AnnouncementRepository;
import com.rohit.repositories.HODRepository;

@Service
public class HODAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final HODRepository hodRepository;

    public HODAnnouncementService(
            AnnouncementRepository announcementRepository,
            HODRepository hodRepository) {

        this.announcementRepository = announcementRepository;
        this.hodRepository = hodRepository;
    }

    private Map<String, Object> response(
            String message, Object data) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("data", data);
        return body;
    }

    private ResponseEntity<?> error(
            String message, HttpStatus status) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }

    private Optional<HOD> findHOD(String username) {
        return hodRepository.findByUserUsername(username);
    }

    private Map<String, Object> toDTO(Announcement announcement) {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("id", announcement.getId());
        data.put("title", announcement.getTitle());
        data.put("description", announcement.getDescription());
        data.put("message", announcement.getDescription());
        data.put("createdAt", announcement.getCreatedAt());

        data.put(
            "departmentId",
            announcement.getDepartment() == null
                ? null
                : announcement.getDepartment().getId()
        );

        data.put(
            "courseId",
            announcement.getCourse() == null
                ? null
                : announcement.getCourse().getId()
        );

        return data;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAll(String username) {

        Optional<HOD> optionalHOD = findHOD(username);

        if (optionalHOD.isEmpty()) {
            return error(
                "HOD profile not found for " + username,
                HttpStatus.NOT_FOUND
            );
        }

        HOD hod = optionalHOD.get();

        if (hod.getDepartment() == null) {
            return error(
                "No department is assigned to this HOD.",
                HttpStatus.CONFLICT
            );
        }

        long departmentId = hod.getDepartment().getId();

        List<Map<String, Object>> announcements =
            announcementRepository
                .findByDepartment_IdOrderByCreatedAtDesc(departmentId)
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(
            response("Announcements fetched successfully", announcements)
        );
    }

    @Transactional
    public ResponseEntity<?> add(
            String username,
            String title,
            String description) {

        if (title == null || title.isBlank()
                || description == null || description.isBlank()) {
            return error(
                "Title and description are required.",
                HttpStatus.BAD_REQUEST
            );
        }

        Optional<HOD> optionalHOD = findHOD(username);

        if (optionalHOD.isEmpty()) {
            return error("HOD profile not found.", HttpStatus.NOT_FOUND);
        }

        HOD hod = optionalHOD.get();

        if (hod.getDepartment() == null) {
            return error(
                "No department is assigned to this HOD.",
                HttpStatus.CONFLICT
            );
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(title.trim());
        announcement.setDescription(description.trim());
        announcement.setCreatedBy(hod.getUser());
        announcement.setDepartment(hod.getDepartment());
        announcement.setCreatedAt(LocalDateTime.now());

        Announcement saved = announcementRepository.save(announcement);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            response(
                "Announcement created successfully",
                toDTO(saved)
            )
        );
    }

    @Transactional
    public ResponseEntity<?> update(
            String username,
            long id,
            String title,
            String description) {

        if (title == null || title.isBlank()
                || description == null || description.isBlank()) {
            return error(
                "Title and description are required.",
                HttpStatus.BAD_REQUEST
            );
        }

        Optional<HOD> optionalHOD = findHOD(username);

        if (optionalHOD.isEmpty()) {
            return error("HOD profile not found.", HttpStatus.NOT_FOUND);
        }

        HOD hod = optionalHOD.get();

        if (hod.getDepartment() == null) {
            return error(
                "No department is assigned to this HOD.",
                HttpStatus.CONFLICT
            );
        }

        long departmentId = hod.getDepartment().getId();

        Optional<Announcement> optionalAnnouncement =
            announcementRepository.findByIdAndDepartment_Id(
                id, departmentId
            );

        if (optionalAnnouncement.isEmpty()) {
            return error(
                "Announcement not found in your department.",
                HttpStatus.NOT_FOUND
            );
        }

        Announcement announcement = optionalAnnouncement.get();
        announcement.setTitle(title.trim());
        announcement.setDescription(description.trim());

        Announcement saved = announcementRepository.save(announcement);

        return ResponseEntity.ok(
            response(
                "Announcement updated successfully",
                toDTO(saved)
            )
        );
    }

    @Transactional
    public ResponseEntity<?> delete(String username, long id) {

        Optional<HOD> optionalHOD = findHOD(username);

        if (optionalHOD.isEmpty()) {
            return error("HOD profile not found.", HttpStatus.NOT_FOUND);
        }

        HOD hod = optionalHOD.get();

        if (hod.getDepartment() == null) {
            return error(
                "No department is assigned to this HOD.",
                HttpStatus.CONFLICT
            );
        }

        long departmentId = hod.getDepartment().getId();

        Optional<Announcement> optionalAnnouncement =
            announcementRepository.findByIdAndDepartment_Id(
                id, departmentId
            );

        if (optionalAnnouncement.isEmpty()) {
            return error(
                "Announcement not found in your department.",
                HttpStatus.NOT_FOUND
            );
        }

        announcementRepository.delete(optionalAnnouncement.get());

        return ResponseEntity.ok(
            response("Announcement deleted successfully", null)
        );
    }
}
