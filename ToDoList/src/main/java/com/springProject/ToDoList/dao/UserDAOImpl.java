package com.springProject.ToDoList.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springProject.ToDoList.entity.TodoUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class UserDAOImpl implements UserDAO{
	
	private EntityManager theEntityManager;
	
	@Autowired
	public UserDAOImpl(EntityManager theEntityManager) {
		this.theEntityManager = theEntityManager;
	}

	@Override
	@Transactional
	public void addUser(TodoUser theUser) {
		List<TodoUser> resultList = findUser(theUser.getUsername());
		if(theUser!=null && (resultList == null || resultList.isEmpty())) {
			theEntityManager.persist(theUser);
		}
	}

	@Override
	public List<TodoUser> findUser(String username) {
		// create query
		TypedQuery<TodoUser> theQuery = theEntityManager.createQuery("FROM TodoUser WHERE username=:username", TodoUser.class);

		// set query parameters
		theQuery.setParameter("username", username);

		// return query results
		return theQuery.getResultList();
	}

	@Override
	public boolean validateUser(String username, String password) {
		
		if(username == null || password == null) {
			return false;
		}
		
		//create query
		TypedQuery<TodoUser> theQuery = theEntityManager.createQuery("FROM TodoUser WHERE username=:username AND password=:password", TodoUser.class);
		
		//set query parameters
		theQuery.setParameter("username", username);
		theQuery.setParameter("password", password);
		
		//fetch query result
		List<TodoUser> tempUser = theQuery.getResultList();
		
		return tempUser!=null && !tempUser.isEmpty();
	}

}
