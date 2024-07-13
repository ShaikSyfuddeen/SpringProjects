package com.springProject.ToDoList.service;

import java.util.List;

import com.springProject.ToDoList.payload.TodoItemDto;
import com.springProject.ToDoList.payload.TodoItemResponse;

public interface TodoItemService {

	public TodoItemDto addTask(TodoItemDto todoItemDto);
	
	public TodoItemResponse getAllTasks(int pageNo, int pageSize, String sortBy, String sortDir);
	
	public TodoItemDto getTaskById(Long id);
	
	public TodoItemDto updateTask(TodoItemDto todoItemDto);
	
	public void deleteTaskById(Long id);
}
