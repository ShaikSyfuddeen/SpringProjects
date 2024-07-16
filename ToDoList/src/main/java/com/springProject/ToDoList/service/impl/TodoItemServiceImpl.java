package com.springProject.ToDoList.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springProject.ToDoList.entity.TodoItem;
import com.springProject.ToDoList.entity.User;
import com.springProject.ToDoList.exception.ResourceNotFoundException;
import com.springProject.ToDoList.payload.TodoItemDto;
import com.springProject.ToDoList.payload.TodoItemResponse;
import com.springProject.ToDoList.repository.TodoItemRepository;
import com.springProject.ToDoList.repository.UserRepository;
import com.springProject.ToDoList.service.TodoItemService;

@Service
public class TodoItemServiceImpl implements TodoItemService{

	private TodoItemRepository todoItemRepository;
	private UserRepository userRepository;
	private ModelMapper mapper;
	
	public TodoItemServiceImpl(TodoItemRepository todoItemRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.todoItemRepository = todoItemRepository;
		this.mapper = modelMapper;
		this.userRepository = userRepository;
	}
	
	public TodoItemDto addTask(String username, TodoItemDto todoItemDto) {
		
		TodoItem todoItem = mapper.map(todoItemDto, TodoItem.class);
		
		User user = getUser(username);
		
		todoItem.setUser(user);
		TodoItem newTask = todoItemRepository.save(todoItem);
		return mapper.map(newTask, TodoItemDto.class);
	}

	@Override
	public TodoItemResponse getAllTasks(String username, int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		// create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		User user = getUser(username);
		
		Page<TodoItem> taskList = todoItemRepository.findAllByUser(user, pageable);
		
		List<TodoItem> tasks = taskList.getContent();
		
		List<TodoItemDto> content = tasks.stream().map(task -> mapper.map(task, TodoItemDto.class)).collect(Collectors.toList());
		
		TodoItemResponse itemResponse = new TodoItemResponse();
		itemResponse.setContent(content);
		itemResponse.setPageNo(taskList.getNumber());
		itemResponse.setPageSize(taskList.getSize());
		itemResponse.setTotalElements(taskList.getTotalElements());
		itemResponse.setTotalPages(taskList.getTotalPages());
		itemResponse.setLast(taskList.isLast());
		
		return itemResponse;
	}

	@Override
	public TodoItemDto getTaskById(String username, Long id) {
		
		User user = getUser(username);
		
		TodoItem item = todoItemRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
		return mapper.map(item, TodoItemDto.class);
	}

	@Override
	public TodoItemDto updateTask(String username, TodoItemDto todoItemDto, Long id) {
		
		User user = getUser(username);
		
		TodoItem item = todoItemRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
		
		item.setName(todoItemDto.getName());
		item.setDescription(todoItemDto.getDescription());
		item.setDeadline(todoItemDto.getDeadline());
		
		TodoItem updatedItem = todoItemRepository.save(item);
		return mapper.map(updatedItem, TodoItemDto.class);
	}

	@Override
	public void deleteTaskById(String username, Long id) {
		
		User user = getUser(username);
		
		TodoItem item = todoItemRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
		
		if(item != null) {
			todoItemRepository.delete(item);
		}
		
	}
	
	private User getUser(String username) {
		return userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}
}
