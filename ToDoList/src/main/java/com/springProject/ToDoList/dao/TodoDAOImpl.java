package com.springProject.ToDoList.dao;

import org.springframework.stereotype.Repository;

import com.springProject.ToDoList.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class TodoDAOImpl implements TodoDAO{

	private EntityManager entityManager;
	
	public TodoDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Users findUser(String username) {
		Users tempUser = entityManager.find(Users.class, username);
		return tempUser;
	}

	@Override
	@Transactional
	public void updateUser(Users user) {
		entityManager.merge(user);
	}

}
