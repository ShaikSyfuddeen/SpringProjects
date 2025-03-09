package com.springboot.blogapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.springboot.blogapp.payload.JWTAuthResponse;
import com.springboot.blogapp.security.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blogapp.entity.User;
import com.springboot.blogapp.payload.LoginDTO;
import com.springboot.blogapp.payload.RegisterDTO;
import com.springboot.blogapp.repository.UserRepository;
import com.springboot.blogapp.service.AuthService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

	private static final Logger l = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthController authController;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private AuthService authService;

	@Autowired
	private ModelMapper mapper;

	@Test
	public void loginTest() {
		LoginDTO dto = new LoginDTO("user1", "user1");
		when(authService.login(dto)).thenReturn("abc");

		ResponseEntity<JWTAuthResponse> response = authController.login(dto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("abc", response.getBody().getAccessToken());

		// Verify that authService.login was called once
		Mockito.verify(authService, times(1)).login(dto);
	}

	@Test
	public void registerTest() {
		RegisterDTO dto = new RegisterDTO("user1", "user1", "user1@email.com", "user1");
		when(authService.register(any(RegisterDTO.class))).thenReturn("User registered successfully");

		ResponseEntity<String> response = authController.register(dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("User registered successfully", response.getBody());
		Mockito.verify(authService, times(1)).register(dto);
	}

}
