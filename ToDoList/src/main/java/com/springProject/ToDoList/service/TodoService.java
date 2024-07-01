package com.springProject.ToDoList.service;

import com.springProject.ToDoList.entity.Users;

public interface TodoService {
	
	public Users findUserByUsername(String username);
	
	public void updateUser(Users user);
}
