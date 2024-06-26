package com.springProject.ToDoList.service;

import com.springProject.ToDoList.entity.TodoUser;

public interface ToDoUserService {
	
	public void addUser(TodoUser theUser);
	
	public boolean validateUser(TodoUser theUser);
	
}
