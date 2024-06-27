package com.springProject.ToDoList.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="todo_list")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="task_id")
	private int taskId;
	
	@Column(name="task_name")
	private String taskName;
	
	@Column(name="task_deadline")
	private Date taskDeadline;
	
	@Column(name="todo_status")
	private String taskStatus;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private TodoUser todoUser;
	
	public Task() {
		
	}

	public Task(String taskName, Date taskDeadline, String taskStatus) {
		this.taskName = taskName;
		this.taskDeadline = taskDeadline;
		this.taskStatus = taskStatus;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getTaskDeadline() {
		return taskDeadline;
	}

	public void setTaskDeadline(Date taskDeadline) {
		this.taskDeadline = taskDeadline;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public TodoUser getTodoUser() {
		return todoUser;
	}

	public void setTodoUser(TodoUser todoUser) {
		this.todoUser = todoUser;
	}

}
