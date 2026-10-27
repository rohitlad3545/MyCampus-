package com.rohit.response_wrapper;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class JWTResponseWrapper {
	private long id;
	private String jwtToken;
	private String role;
}
