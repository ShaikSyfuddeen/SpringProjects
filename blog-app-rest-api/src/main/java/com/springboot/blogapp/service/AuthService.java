package com.springboot.blogapp.service;

import com.springboot.blogapp.payload.LoginDTO;
import com.springboot.blogapp.payload.RegisterDTO;

public interface AuthService {
	
	String login(LoginDTO loginDTO);
	
	String register(RegisterDTO registerDTO);
}
