package com.rohit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.dtos.UserDTO;
import com.rohit.entites.User;
import com.rohit.response_wrapper.CustomResponse;
import com.rohit.services.UserService;





@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<CustomResponse> register(
            @RequestBody User user) {

        return userService.register(user);
    }


    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(
            @RequestBody UserDTO userDTO) {

        return userService.login(userDTO);
    }
}
