package com.rohit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.entites.Department;
import com.rohit.repositories.DepartmentRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.Response;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    Response response;


    // GET ALL DEPARTMENTS

    public ResponseEntity<CustomResponse> getAllDepartments() {

        List<Department> departments =
                departmentRepository.findAll();

        return response.send(
                "Departments fetched successfully",
                departments,
                HttpStatus.OK
        );
    }


    // GET DEPARTMENT BY ID

    public ResponseEntity<CustomResponse> getDepartmentById(
            Long id) {

        Optional<Department> department =
                departmentRepository.findById(id);

        if (department.isEmpty()) {

            return response.send(
                    "Department not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        return response.send(
                "Department found",
                department.get(),
                HttpStatus.OK
        );
    }


    // ADD DEPARTMENT

    public ResponseEntity<CustomResponse> addDepartment(
            Department department) {

        Department savedDepartment =
                departmentRepository.save(department);

        return response.send(
                "Department added successfully",
                savedDepartment,
                HttpStatus.OK
        );
    }


    // UPDATE DEPARTMENT

    public ResponseEntity<CustomResponse> updateDepartment(
            Long id,
            Department department) {

        Optional<Department> existingDepartment =
                departmentRepository.findById(id);

        if (existingDepartment.isEmpty()) {

            return response.send(
                    "Department not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Department existing =
                existingDepartment.get();

        existing.setName(
                department.getName()
        );

        existing.setDescription(
                department.getDescription()
        );


        Department updatedDepartment =
                departmentRepository.save(existing);


        return response.send(
                "Department updated successfully",
                updatedDepartment,
                HttpStatus.OK
        );
    }


    // DELETE DEPARTMENT

    public ResponseEntity<CustomResponse> deleteDepartment(
            Long id) {

        Optional<Department> department =
                departmentRepository.findById(id);

        if (department.isEmpty()) {

            return response.send(
                    "Department not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        departmentRepository.deleteById(id);

        return response.send(
                "Department deleted successfully",
                null,
                HttpStatus.OK
        );
    }

}