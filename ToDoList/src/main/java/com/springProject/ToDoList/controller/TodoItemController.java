package com.springProject.ToDoList.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springProject.ToDoList.payload.TodoItemDto;
import com.springProject.ToDoList.payload.TodoItemResponse;
import com.springProject.ToDoList.service.TodoItemService;
import com.springProject.ToDoList.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todo-app")
public class TodoItemController {
	
	private TodoItemService todoItemService;
	
	public TodoItemController(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	@PostMapping("/add-task")
	public ResponseEntity<TodoItemDto> addTask(@Valid @RequestBody TodoItemDto todoItemDto) {
		
		return new ResponseEntity<TodoItemDto>(todoItemService.addTask(todoItemDto), HttpStatus.CREATED);
	}
	
	@GetMapping({"/tasks", ""})
	public TodoItemResponse getAllTasks(
			@RequestParam(value="pageNo", defaultValue=AppConstants.DEFAULT_PAGE_NO, required=false) int pageNo,
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false) int pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir
			){
		
		return todoItemService.getAllTasks(pageNo, pageSize, sortBy, sortDir);
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<TodoItemDto> getTaskById(@PathVariable("id") Long id){
		return ResponseEntity.ok(todoItemService.getTaskById(id));
	}
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<TodoItemDto> updateTask(@Valid @RequestBody TodoItemDto todoItemDto){
		return ResponseEntity.ok(todoItemService.updateTask(todoItemDto));
	}
	
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable("id") Long id){
		todoItemService.deleteTaskById(id);
		return ResponseEntity.ok("Task with id: " + id + " deleted successfully");
	}
}
