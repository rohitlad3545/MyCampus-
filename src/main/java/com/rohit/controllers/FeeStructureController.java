package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rohit.entites.FeeStructure;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.FeeStructureService;

@RestController
@RequestMapping("/api/v1/admin/fee-structure")
public class FeeStructureController {

    @Autowired
    private FeeStructureService feeStructureService;

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAll() {
        return feeStructureService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getById(
            @PathVariable Long id) {
        return feeStructureService.getById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> add(
            @RequestBody FeeStructure feeStructure) {
        return feeStructureService.add(feeStructure);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomResponse> update(
            @PathVariable Long id,
            @RequestBody FeeStructure feeStructure) {
        return feeStructureService.update(id, feeStructure);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> delete(
            @PathVariable Long id) {
        return feeStructureService.delete(id);
    }
}