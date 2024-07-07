package com.springboot.blogapp.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.entity.Role;
import com.springboot.blogapp.entity.User;
import com.springboot.blogapp.exception.BlogAPIException;
import com.springboot.blogapp.payload.LoginDTO;
import com.springboot.blogapp.payload.RegisterDTO;
import com.springboot.blogapp.repository.RoleRepository;
import com.springboot.blogapp.repository.UserRepository;
import com.springboot.blogapp.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String login(LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), 
																			loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User logged in successfully";
	}

	@Override
	public String register(RegisterDTO registerDTO) {
		
		// add check for username exists in database
		if(userRepository.existsByUsername(registerDTO.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Userame already exists");
		}
		
		// add check for email exists i database
		if(userRepository.existsByEmail(registerDTO.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
		}
		
		User user = new User();
		user.setName(registerDTO.getName());
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRole(roles);
		
		userRepository.save(user);
		
		return "User registered successfully";
	}

}
