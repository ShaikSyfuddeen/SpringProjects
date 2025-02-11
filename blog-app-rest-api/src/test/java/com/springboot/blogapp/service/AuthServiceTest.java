package com.springboot.blogapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.blogapp.entity.User;
import com.springboot.blogapp.payload.LoginDTO;
import com.springboot.blogapp.payload.RegisterDTO;
import com.springboot.blogapp.repository.UserRepository;
import com.springboot.blogapp.security.JwtTokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

	@Autowired
	private AuthService authService;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	private Authentication authentication;
	
	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Test
	public void loginTest() {
		LoginDTO dto = new LoginDTO("user2", "user2");
		authentication = new UsernamePasswordAuthenticationToken(dto.getUsernameOrEmail(), dto.getPassword());
		when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
		when(jwtTokenProvider.generateToken(authentication)).thenReturn("sampleToken");
		assertEquals("sampleToken", authService.login(dto));
		
	}
	
	@Test
	public void registerTest() {
		RegisterDTO dto = new RegisterDTO("user1", "user1", "user1@email.com", "user1");
		User user = mapper.map(dto, User.class);
		when(userRepository.existsByUsername("user1")).thenReturn(false);
		when(userRepository.existsByEmail("user1@email.com")).thenReturn(false);
		when(userRepository.save(Mockito.any())).thenReturn(user);
		assertEquals("User registered successfully", authService.register(dto));
	}
	
	
}
