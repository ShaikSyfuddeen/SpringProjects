package com.springboot.blogapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blogapp.entity.User;
import com.springboot.blogapp.payload.RegisterDTO;
import com.springboot.blogapp.repository.UserRepository;
import com.springboot.blogapp.security.JwtTokenProvider;
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
	
//	public void loginTest() {
//		LoginDTO dto = new LoginDTO("user2", "'$2a$10$GMjAfOBBONkAZAL5a6OJbuNq1nfFcnnYcOoDZ/Et1LNDqX8LpY9F2'");
//		
//		when(jwtTokenProvider.validateToken(validJwtToken)).thenReturn(true);
//		when(authenticationManager.authenticate(Mockito.anyString()))
//			.thenReturn(new UsernamePasswordAuthenticationToken("user", null, new ArrayList<>()));
//	}
	
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
							.content(new ObjectMapper().writeValueAsString(dto)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			l.error("Exception occured while validating the '/signup' endpoint, " + e);
		}
	}

}
