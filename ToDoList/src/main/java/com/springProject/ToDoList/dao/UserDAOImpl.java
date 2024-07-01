package com.springProject.ToDoList.dao;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAO{
	
	EntityManager entityManager;
	
	public UserDAOImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

}
