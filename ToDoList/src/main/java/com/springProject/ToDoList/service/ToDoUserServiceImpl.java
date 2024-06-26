package com.springProject.ToDoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springProject.ToDoList.dao.UserDAO;
import com.springProject.ToDoList.entity.TodoUser;

@Service
public class ToDoUserServiceImpl implements ToDoUserService{
	
	private UserDAO theUserDAO;

	@Autowired
	public ToDoUserServiceImpl(UserDAO theUserDAO) {
		this.theUserDAO = theUserDAO;
	}

	@Override
	public void addUser(TodoUser theUser) {
		theUserDAO.addUser(theUser);
	}

	@Override
	public boolean validateUser(TodoUser theUser) {
		if(theUser != null) {
			return theUserDAO.validateUser(theUser.getUsername(), theUser.getPassword());
		}
		return false;
	}

}
