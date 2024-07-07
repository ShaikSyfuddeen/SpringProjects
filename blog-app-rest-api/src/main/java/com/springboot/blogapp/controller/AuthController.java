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

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	// Build login REST API
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO){
		String token = authService.login(loginDTO);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	// Build register REST API
	@PostMapping(value = {"/signup", "/register"})
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
		String response = authService.register(registerDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}