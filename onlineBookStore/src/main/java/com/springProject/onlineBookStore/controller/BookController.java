package com.springProject.onlineBookStore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springProject.onlineBookStore.entity.Book;
import com.springProject.onlineBookStore.service.BookStoreService;

@Controller
@RequestMapping("/")
public class BookController {

	private BookStoreService bookStoreService;

	public BookController(BookStoreService theBookStoreService) {
		bookStoreService = theBookStoreService;
	}

	@GetMapping("onlinebookstore")
	public String sayHello() {
		return "hello, welcome to online book store";
	}

	@GetMapping("onlinebookstore/showBooks")
	public String showBooks(Model theModel) {

		List<Book> books = bookStoreService.findAll();
		theModel.addAttribute("books", books);

		return "/books-list";
	}

	@GetMapping("onlinebookstore/showBooks/{bookId}")
	public String getBook(@PathVariable int bookId, Model theModel) {

		Book book = bookStoreService.find(bookId);
		theModel.addAttribute("book", book);

		return "/book-info";
	}

	@GetMapping("onlinebookstore/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Book theBook = new Book();
		theModel.addAttribute("book", theBook);
		
		return "/book-form";
	}

	@PostMapping("onlinebookstore/saveBook")
	public String addBook(@ModelAttribute("book") Book theBook) {
		bookStoreService.save(theBook);
		return "redirect:/onlinebookstore/showBooks";
	}

}
