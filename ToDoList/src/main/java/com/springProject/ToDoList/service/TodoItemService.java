package com.springProject.ToDoList.service;

import java.util.List;

import com.springProject.ToDoList.payload.TodoItemDto;

public interface TodoItemService {

	public TodoItemDto addTask(TodoItemDto todoItemDto);
	
	public List<TodoItemDto> getAllTasks();
	
	public TodoItemDto getTaskById(Long id);
	
	public TodoItemDto updateTask(TodoItemDto todoItemDto);
	
	public void deleteTaskById(Long id);
}
