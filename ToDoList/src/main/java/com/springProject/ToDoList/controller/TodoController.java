package com.springProject.ToDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springProject.ToDoList.entity.Task;
import com.springProject.ToDoList.entity.Users;
import com.springProject.ToDoList.service.TodoService;

@Controller
@RequestMapping("/TodoApp")
public class TodoController {
	
	TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping("/Add-Task")
	public String showFormForAddTask(@RequestParam("userId") String userId, Model theModel) {
		Users user = todoService.findUserByUsername(userId);
		Task task = new Task();
		theModel.addAttribute("user", user);
		theModel.addAttribute("task", task);
		return "Add-Task";
	}
	
	@PostMapping("/Add-Task")
	public String addTask(@ModelAttribute("user") Users user, @ModelAttribute("task") Task task, Model theModel) {
		task.setUser(user);
		user.addTask(task);
		todoService.updateUser(user);
		return "/home";
	}
}
