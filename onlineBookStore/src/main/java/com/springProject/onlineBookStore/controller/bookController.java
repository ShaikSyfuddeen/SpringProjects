package com.springProject.onlineBookStore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class bookController {

	@GetMapping("onlinebookstore")
	public String sayHello() {
		return "hello";
	}
	
	@GetMapping("onlinebookstore/showBooks")
	public String showBooks() {
		return "We don't have any books right now";
	}
	
	@GetMapping("onlinebookstore/showBooks/{bookId}")
	public String getBook(@PathVariable int bookId) {
		return "Fetching the book with id: " + bookId;
	}
}
