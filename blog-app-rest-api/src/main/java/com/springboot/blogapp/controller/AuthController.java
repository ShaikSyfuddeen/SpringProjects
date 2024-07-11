package com.springboot.blogapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogapp.payload.JWTAuthResponse;
import com.springboot.blogapp.payload.LoginDTO;
import com.springboot.blogapp.payload.RegisterDTO;
import com.springboot.blogapp.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(
		name = "CRUD REST APIs for Authentication Resource"
		)
public class AuthController {
	
	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	// Build login REST API
	@Operation(
			summary = "Login user REST API",
			description = "Login user REST API is used login the user and return jwt token"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
			)
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO){
		String token = authService.login(loginDTO);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	// Build register REST API
	@Operation(
			summary = "Register user REST API",
			description = "Register user REST API is used register the user in the database"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
			)
	@PostMapping(value = {"/signup", "/register"})
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
		String response = authService.register(registerDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
