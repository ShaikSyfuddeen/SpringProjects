package com.springProject.ToDoList.dao;

import com.springProject.ToDoList.entity.Users;

public interface TodoDAO {
	
	public Users findUser(String username);
	
	public void updateUser(Users user);

}
