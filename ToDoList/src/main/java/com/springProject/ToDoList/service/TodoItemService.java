package com.springProject.ToDoList.service;

import java.util.List;

import com.springProject.ToDoList.payload.TodoItemDto;
import com.springProject.ToDoList.payload.TodoItemResponse;

public interface TodoItemService {

	public TodoItemDto addTask(String username, TodoItemDto todoItemDto);
	
	public TodoItemResponse getAllTasks(String username, int pageNo, int pageSize, String sortBy, String sortDir);
	
	public TodoItemDto getTaskById(String username, Long id);
	
	public TodoItemDto updateTask(String username, TodoItemDto todoItemDto, Long id);
	
	public void deleteTaskById(String username, Long id);
}
