package com.springProject.ToDoList.payload;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class TodoItemDto {
	
	private Long id;
	
	@NotEmpty
	@Size(min=2, message = "Task name should be minimum 2 charecters long")
	private String name;
	
	@NotEmpty
	@Size(min=10, message = "Task description should be minimum 10 charecters long")
	private String description;
	
	private Date deadline;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
