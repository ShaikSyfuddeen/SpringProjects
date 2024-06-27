package com.springProject.ToDoList.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class TodoUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@OneToMany(mappedBy = "todoUser", cascade = {CascadeType.DETACH,CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Task> todoList;

	public TodoUser() {
		
	}
	
	public TodoUser(int userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "TodoUser [userId=" + userId + ", username=" + username + ", password=" + password + "]";
	}
	
}
