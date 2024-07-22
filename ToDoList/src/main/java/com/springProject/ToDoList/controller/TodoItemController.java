package com.springProject.ToDoList.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
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
import com.springProject.ToDoList.security.JwtTokenProvider;
import com.springProject.ToDoList.service.TodoItemService;
import com.springProject.ToDoList.utils.AppConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todo-app")
public class TodoItemController {
	
	private TodoItemService todoItemService;
	
	public TodoItemController(TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	@PostMapping("/add-task")
	public ResponseEntity<TodoItemDto> addTask(HttpServletRequest request, @Valid @RequestBody TodoItemDto todoItemDto) {
		
		String username = request.getUserPrincipal().getName();
		return new ResponseEntity<TodoItemDto>(todoItemService.addTask(username, todoItemDto), HttpStatus.CREATED);
	}
	
	@GetMapping({"/tasks", ""})
	public TodoItemResponse getAllTasks(
			HttpServletRequest request,
			@RequestParam(value="pageNo", defaultValue=AppConstants.DEFAULT_PAGE_NO, required=false) int pageNo,
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false) int pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir
			){
		
		String username = request.getUserPrincipal().getName();
		return todoItemService.getAllTasks(username, pageNo, pageSize, sortBy, sortDir);
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<TodoItemDto> getTaskById(HttpServletRequest request, @PathVariable("id") Long id){
		
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(todoItemService.getTaskById(username, id));
	}
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<TodoItemDto> updateTask(HttpServletRequest request, @Valid @RequestBody TodoItemDto todoItemDto, @PathVariable("id") Long id){
		
		String username = request.getUserPrincipal().getName();
		return ResponseEntity.ok(todoItemService.updateTask(username, todoItemDto, id));
	}
	
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<String> deleteTask(HttpServletRequest request, @PathVariable("id") Long id){
		
		String username = request.getUserPrincipal().getName();
		todoItemService.deleteTaskById(username, id);
		return ResponseEntity.ok("Task with id: " + id + " deleted successfully");
	}

}
