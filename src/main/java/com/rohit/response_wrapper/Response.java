package com.rohit.response_wrapper;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Response {

    public ResponseEntity<CustomResponse> send(
            String message,
            Object data,
            HttpStatus httpStatus) {

        CustomResponse customResponse =
                new CustomResponse();

        customResponse.setMessage(message);
        customResponse.setData(data);

        return new ResponseEntity<>(
                customResponse,
                httpStatus
        );
    }
}