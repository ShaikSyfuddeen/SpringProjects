package com.springProject.ToDoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springProject.ToDoList.entity.TodoUser;
import com.springProject.ToDoList.service.ToDoUserService;

@Controller
@RequestMapping("/")
public class ToDoController {
	
	private ToDoUserService todoUserService;
	
	@Autowired
	public ToDoController(ToDoUserService todoUserService) {
		this.todoUserService = todoUserService;
	}
	
	@GetMapping("/nav")
	public String getNavBar() {
		return "nav";
	}

	@GetMapping("/createUser")
	public String showFormToRegisterUser(Model theModel) {
		TodoUser theTodoUser = new TodoUser();
		theModel.addAttribute("todoUser", theTodoUser);
		return "security/sign-up";
	}
	
	@PostMapping("/register-user")
	public String registerUser(@ModelAttribute("todoUser") TodoUser theTodoUser) {
		todoUserService.addUser(theTodoUser);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String displayUserLoginPage(Model theModel) {
		TodoUser theTodoUser = new TodoUser();
		theModel.addAttribute("todoUser", theTodoUser);
		return "security/login";
	}
	
	@PostMapping("/validate-user")
	public String validateUser(@ModelAttribute("todoUser") TodoUser theTodoUser, Model theModel) {
		boolean loginStatus = todoUserService.validateUser(theTodoUser);
		theModel.addAttribute("loginStatus", loginStatus);
		return "MyTodoList";
	}
	

}
