package com.springboot.blogapp.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.springboot.blogapp.payload.LoginDTO;
import com.springboot.blogapp.security.JwtAuthenticationEntryPoint;
import com.springboot.blogapp.security.JwtTokenProvider;

//@RunWith(SpringRunner.class)
//@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	private String validJwtToken;
	
	private String invalidJwtToken;
	
//	@Before
	private void setUp() {
		validJwtToken = "Bearer valid.jwt.token";
        invalidJwtToken = "Bearer invalid.jwt.token";
	}
	
	public void loginTest() {
		LoginDTO dto = new LoginDTO("user2", "'$2a$10$GMjAfOBBONkAZAL5a6OJbuNq1nfFcnnYcOoDZ/Et1LNDqX8LpY9F2'");
		
//		when(jwtTokenProvider.validateToken(validJwtToken)).thenReturn(true);
//		when(authenticationManager.authenticate(Mockito.anyString()))
//			.thenReturn(new UsernamePasswordAuthenticationToken("user", null, new ArrayList<>()));
	}

}
