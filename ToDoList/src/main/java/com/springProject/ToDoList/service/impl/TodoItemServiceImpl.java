package com.springProject.ToDoList.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springProject.ToDoList.entity.TodoItem;
import com.springProject.ToDoList.exception.ResourceNotFoundException;
import com.springProject.ToDoList.payload.TodoItemDto;
import com.springProject.ToDoList.repository.TodoItemRepository;
import com.springProject.ToDoList.service.TodoItemService;

@Service
public class TodoItemServiceImpl implements TodoItemService{

	private TodoItemRepository todoItemRepository;
	private ModelMapper mapper;
	
	public TodoItemServiceImpl(TodoItemRepository todoItemRepository, ModelMapper modelMapper) {
		this.todoItemRepository = todoItemRepository;
		this.mapper = modelMapper;
	}
	
	public TodoItemDto addTask(TodoItemDto todoItemDto) {
		
		TodoItem todoItem = mapper.map(todoItemDto, TodoItem.class);
		TodoItem newTask = todoItemRepository.save(todoItem);
		return mapper.map(newTask, TodoItemDto.class);
	}

	@Override
	public List<TodoItemDto> getAllTasks() {
		
		List<TodoItem> tasks = todoItemRepository.findAll();
		
		List<TodoItemDto> tasksDto = tasks.stream().map(task -> mapper.map(task, TodoItemDto.class)).collect(Collectors.toList());
		
		return tasksDto;
	}

	@Override
	public TodoItemDto getTaskById(Long id) {
		
		TodoItem item = todoItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
		return mapper.map(item, TodoItemDto.class);
	}

	@Override
	public TodoItemDto updateTask(TodoItemDto todoItemDto) {
		
		TodoItem item = todoItemRepository.findById(todoItemDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Task", "id", todoItemDto.getId()));
		
		item.setName(todoItemDto.getName());
		item.setDescription(todoItemDto.getDescription());
		item.setDeadline(todoItemDto.getDeadline());
		
		TodoItem updatedItem = todoItemRepository.save(item);
		return mapper.map(updatedItem, TodoItemDto.class);
	}

	@Override
	public void deleteTaskById(Long id) {
		
		TodoItem item = todoItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
		
		if(item != null) {
			todoItemRepository.delete(item);
		}
		
	}
}
