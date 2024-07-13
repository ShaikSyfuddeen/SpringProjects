package com.springProject.ToDoList.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springProject.ToDoList.entity.Role;
import com.springProject.ToDoList.entity.User;
import com.springProject.ToDoList.exception.TodoAPIException;
import com.springProject.ToDoList.payload.LoginDTO;
import com.springProject.ToDoList.payload.RegisterDTO;
import com.springProject.ToDoList.repository.RoleRepository;
import com.springProject.ToDoList.repository.UserRepository;
import com.springProject.ToDoList.security.JwtTokenProvider;
import com.springProject.ToDoList.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String register(RegisterDTO registerDTO) {
		
		//check if user with given username already exists
		if(userRepository.existsByUsername(registerDTO.getUsername())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		
		//check is user with given email id already exists
		if(userRepository.existsByEmail(registerDTO.getEmail())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email id already exists");
		}
		
		User user = new User();
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "User registered successfully";
	}

	@Override
	public String login(LoginDTO loginDTO) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}

}
