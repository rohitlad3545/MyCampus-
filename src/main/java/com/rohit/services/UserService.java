package com.rohit.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rohit.dtos.UserDTO;
import com.rohit.entites.Admin;
import com.rohit.entites.Faculty;
import com.rohit.entites.HOD;
import com.rohit.entites.Student;
import com.rohit.entites.User;
import com.rohit.jwt.JWTTokenGenerator;
import com.rohit.repositories.AdminRepository;
import com.rohit.repositories.FacultyRepository;
import com.rohit.repositories.HODRepository;
import com.rohit.repositories.StudentRepository;
import com.rohit.repositories.UserRepository;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.response_wrapper.JWTResponseWrapper;
import com.rohit.response_wrapper.Response;

@Service
public class UserService {

    @Autowired
    Response response;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    HODRepository hodRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTResponseWrapper jwtResponseWrapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    MyUserDetailsService myUserDetailsService;


    // =========================
    // REGISTER
    // =========================

    @Transactional
    public ResponseEntity<CustomResponse> register(User user) {

        // Check username
        if (userRepository.existsByUsername(user.getUsername())) {

            return response.send(
                    "Username already exists",
                    null,
                    HttpStatus.FOUND
            );
        }


        // Encode password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );


        // Save User
        User savedUser =
                userRepository.save(user);


        // Get Role
        String role =
                savedUser.getRole().name();


        // =========================
        // STUDENT
        // =========================

        if (role.equals("STUDENT")) {

            Student student = new Student();

            student.setUser(savedUser);

            Student savedStudent =
                    studentRepository.save(student);

            return response.send(
                    "Student Registered",
                    savedStudent,
                    HttpStatus.OK
            );
        }


        // =========================
        // FACULTY
        // =========================

        else if (role.equals("FACULTY")) {

            Faculty faculty = new Faculty();

            faculty.setUser(savedUser);

            Faculty savedFaculty =
                    facultyRepository.save(faculty);

            return response.send(
                    "Faculty Registered",
                    savedFaculty,
                    HttpStatus.OK
            );
        }


        // =========================
        // ADMIN
        // =========================

        else if (role.equals("ADMIN")) {

            Admin admin = new Admin();

            admin.setUser(savedUser);

            Admin savedAdmin =
                    adminRepository.save(admin);

            return response.send(
                    "Admin Registered",
                    savedAdmin,
                    HttpStatus.OK
            );
        }


        // =========================
        // HOD
        // =========================

        else if (role.equals("HOD")) {

            HOD hod = new HOD();

            hod.setUser(savedUser);

            HOD savedHOD =
                    hodRepository.save(hod);

            return response.send(
                    "HOD Registered",
                    savedHOD,
                    HttpStatus.OK
            );
        }


        // =========================
        // INVALID ROLE
        // =========================

        else {

            return response.send(
                    "Invalid Role",
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    // =========================
    // LOGIN
    // =========================

    public ResponseEntity<CustomResponse> login(
            UserDTO userDTO) {

        // =========================
        // AUTHENTICATION
        // =========================

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(),
                            userDTO.getPassword()
                    )
            );

        } catch (AuthenticationException e) {

            return response.send(
                    "Bad Credentials",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        // =========================
        // FIND USER
        // =========================

        Optional<User> userOptional =
                userRepository.findByUsername(
                        userDTO.getUsername()
                );


        if (userOptional.isEmpty()) {

            return response.send(
                    "User not found",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }


        User existingUser =
                userOptional.get();


        // =========================
        // GET ROLE
        // =========================

        String role =
                existingUser.getRole().name();


        // =========================
        // LOAD USER DETAILS
        // =========================

        UserDetails userDetails =
                myUserDetailsService.loadUserByUsername(
                        userDTO.getUsername()
                );


        // =========================
        // GENERATE JWT
        // =========================

        final String JWTtoken =
                jwtTokenGenerator.generateToken(
                        userDetails,
                        role
                );


        // =========================
        // PROFILE ID
        // =========================

        long id;


        // =========================
        // STUDENT
        // =========================

        if (role.equals("STUDENT")) {

            Optional<Student> student =
                    studentRepository.findByUserId(
                            existingUser.getId()
                    );


            if (student.isEmpty()) {

                return response.send(
                        "Student profile not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }


            id = student.get().getId();
        }


        // =========================
        // FACULTY
        // =========================

        else if (role.equals("FACULTY")) {

            Optional<Faculty> faculty =
                    facultyRepository.findByUserId(
                            existingUser.getId()
                    );


            if (faculty.isEmpty()) {

                return response.send(
                        "Faculty profile not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }


            id = faculty.get().getId();
        }


        // =========================
        // ADMIN
        // =========================

        else if (role.equals("ADMIN")) {

            Optional<Admin> admin =
                    adminRepository.findByUserId(
                            existingUser.getId()
                    );


            if (admin.isEmpty()) {

                return response.send(
                        "Admin profile not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }


            id = admin.get().getId();
        }


        // =========================
        // HOD
        // =========================

        else if (role.equals("HOD")) {

            Optional<HOD> hod =
                    hodRepository.findByUserId(
                            existingUser.getId()
                    );


            if (hod.isEmpty()) {

                return response.send(
                        "HOD profile not found",
                        null,
                        HttpStatus.NOT_FOUND
                );
            }


            id = hod.get().getId();
        }


        // =========================
        // INVALID ROLE
        // =========================

        else {

            return response.send(
                    "Invalid Role",
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }


        // =========================
        // JWT RESPONSE
        // =========================

        jwtResponseWrapper.setId(id);

        jwtResponseWrapper.setRole(role);

        jwtResponseWrapper.setJwtToken(JWTtoken);


        return response.send(
                "Login Success!",
                jwtResponseWrapper,
                HttpStatus.OK
        );
    }
}