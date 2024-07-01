package com.springProject.ToDoList.service;

import org.springframework.stereotype.Service;

import com.springProject.ToDoList.dao.TodoDAO;
import com.springProject.ToDoList.entity.Users;

@Service
public class TodoServiceImpl implements TodoService{
	
	private TodoDAO todoDAO;
	
	public TodoServiceImpl(TodoDAO todoDAO) {
		this.todoDAO = todoDAO;
	}

	@Override
	public Users findUserByUsername(String username) {
		Users user = todoDAO.findUser(username);
		return user;
	}

	@Override
	public void updateUser(Users user) {
		todoDAO.updateUser(user);
	}

}
