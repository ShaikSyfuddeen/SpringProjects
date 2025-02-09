package com.springboot.blogapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.springboot.blogapp.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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
		when(authService.login(dto)).thenReturn("");
		
		try {
			mockMvc.perform(post("/api/auth/login")
							.contentType(MediaType.APPLICATION_JSON)
							.content(new ObjectMapper().writeValueAsString(dto)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void registerTest() {
		
		RegisterDTO dto = new RegisterDTO("user3", "user3", "user3@email.com", "user3");
		User user = mapper.map(dto, User.class);
		when(userRepository.existsByUsername("user3")).thenReturn(false);
		when(userRepository.existsByEmail("user3@email.com")).thenReturn(false);
		when(userRepository.save(Mockito.any())).thenReturn(user);
		when(authService.register(any(RegisterDTO.class))).thenReturn("User registered successfully");
		
		try {
			mockMvc.perform(post("/api/auth/signup")
							.contentType(MediaType.APPLICATION_JSON)
							.content(new ObjectMapper().writeValueAsString(dto))
							)
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			l.error("Exception occured while validating the '/signup' endpoint, " + e);
		}
	}

}
