package com.springProject.ToDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TodoLoginController {
	
	@GetMapping("/")
	public String redirectToHome() {
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String showHome() {
		return "home";
	}

	@GetMapping("/TodoApp/showMyLoginPage")
	public String showMyLoginPage() {
		return "fancy-login";
	}

	// add a request mapping for /access-denied
	@GetMapping("/TodoApp/access-denied")
	public String accessDenied(){
		return "access-denied";
	}
}
