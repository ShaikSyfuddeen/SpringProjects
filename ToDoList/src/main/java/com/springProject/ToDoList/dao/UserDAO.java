package com.springProject.ToDoList.dao;

import java.util.List;

import com.springProject.ToDoList.entity.TodoUser;

public interface UserDAO {
	
	public void addUser(TodoUser theUser);
	
	public List<TodoUser> findUser(String username);
	
	public boolean validateUser(String username, String password);
}
