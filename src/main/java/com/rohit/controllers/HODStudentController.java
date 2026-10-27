
package com.rohit.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.HODService;
import com.rohit.services.HODStudentService;

@RestController
@RequestMapping("/api/v1/hod/student")
public class HODStudentController {

    @Autowired
    private HODStudentService hodStudentService;

    @Autowired
    private HODService hodService;

    // Get the currently authenticated HOD profile
    @GetMapping("/me")
    public ResponseEntity<?> getMyHodProfile(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Authentication required"));
        }

        return hodService.getMyProfile(principal.getName());
    }

    // Get students belonging to the HOD's department
    @GetMapping("/all/{id}")
    public ResponseEntity<CustomResponse> getStudents(
            @PathVariable long id) {

        return hodStudentService.getStudentsByHOD(id);
    }
}
