package com.springProject.ToDoList.service;

import com.springProject.ToDoList.payload.LoginDTO;
import com.springProject.ToDoList.payload.RegisterDTO;

public interface AuthService {
	
	public String register(RegisterDTO registerDTO);
	
	public String login(LoginDTO loginDTO);

}
