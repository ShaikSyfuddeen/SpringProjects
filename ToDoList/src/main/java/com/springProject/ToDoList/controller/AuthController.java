package com.springProject.ToDoList.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springProject.ToDoList.payload.JWTAuthResponse;
import com.springProject.ToDoList.payload.LoginDTO;
import com.springProject.ToDoList.payload.RegisterDTO;
import com.springProject.ToDoList.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	//login user REST API
	@PostMapping({"/login", "/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO){
		String token = authService.login(loginDTO);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	//register user REST API
	@PostMapping({"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
		String response = authService.register(registerDTO);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

}
