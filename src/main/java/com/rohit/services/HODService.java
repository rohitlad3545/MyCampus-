
package com.rohit.services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.HOD;
import com.rohit.repositories.HODRepository;

@Service
public class HODService {

    private final HODRepository hodRepository;

    public HODService(HODRepository hodRepository) {
        this.hodRepository = hodRepository;
    }

    public ResponseEntity<?> getMyProfile(String username) {

        Optional<HOD> optionalHOD =
                hodRepository.findByUserUsername(username);

        if (optionalHOD.isEmpty()) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("message", "HOD profile not found for " + username);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error);
        }

        HOD hod = optionalHOD.get();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", hod.getId());
        data.put("name", hod.getName());
        data.put("email", hod.getEmail());
        data.put("phone", hod.getPhone());

        // LinkedHashMap permits null values.
        data.put(
                "departmentId",
                hod.getDepartment() == null
                        ? null
                        : hod.getDepartment().getId()
        );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "HOD profile fetched successfully");
        body.put("data", data);

        return ResponseEntity.ok(body);
    }
}
