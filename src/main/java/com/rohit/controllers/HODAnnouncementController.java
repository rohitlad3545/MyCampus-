
package com.rohit.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.services.HODAnnouncementService;

@RestController
@RequestMapping("/api/v1/hod/announcements")
public class HODAnnouncementController {

    private final HODAnnouncementService announcementService;

    public HODAnnouncementController(
            HODAnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Authentication required."));
        }

        return announcementService.getAll(principal.getName());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            Principal principal,
            @RequestBody AnnouncementRequest request) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Authentication required."));
        }

        return announcementService.add(
            principal.getName(),
            request.getTitle(),
            request.getDescription() != null
                ? request.getDescription()
                : request.getMessage()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            Principal principal,
            @PathVariable long id,
            @RequestBody AnnouncementRequest request) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Authentication required."));
        }

        return announcementService.update(
            principal.getName(),
            id,
            request.getTitle(),
            request.getDescription() != null
                ? request.getDescription()
                : request.getMessage()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(
            Principal principal,
            @PathVariable long id) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Authentication required."));
        }

        return announcementService.delete(principal.getName(), id);
    }

    public static class AnnouncementRequest {
        private String title;
        private String description;
        private String message;

        public AnnouncementRequest() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
