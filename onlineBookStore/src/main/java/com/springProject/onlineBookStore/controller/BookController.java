package com.springProject.onlineBookStore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springProject.onlineBookStore.entity.Book;
import com.springProject.onlineBookStore.service.BookStoreService;

@Controller
@RequestMapping("/onlinebookstore")
public class BookController {

	private BookStoreService bookStoreService;

	public BookController(BookStoreService theBookStoreService) {
		bookStoreService = theBookStoreService;
	}

	@GetMapping("/booksList")
	public String showBooks(Model theModel) {

		List<Book> books = bookStoreService.findAll();
		theModel.addAttribute("books", books);

		return "/books-list";
	}

	@GetMapping("/bookList/{bookId}")
	public String getBookById(@PathVariable int bookId, Model theModel) {

		Book book = bookStoreService.find(bookId);
		theModel.addAttribute("book", book);

		return "/book-info";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Book theBook = new Book();
		theModel.addAttribute("book", theBook);
		
		return "/book-form";
	}

	@PostMapping("/saveBook")
	public String saveBook(@ModelAttribute("book") Book theBook) {
		bookStoreService.save(theBook);
		return "redirect:/onlinebookstore/booksList";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("bookId") int theId, Model theModel) {
		
		Book theBook = bookStoreService.find(theId);
		theModel.addAttribute("book", theBook);
		
		return "/book-form";	
	}
	
	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute("book") Book theBook) {
		bookStoreService.update(theBook);
		return "redirect:/onlinebookstore/booksList";
	}
	
	@GetMapping("/delete")
	public String deleteBookById(@RequestParam("bookId") int id, Model theModel) {
		bookStoreService.deleteById(id);
		return "redirect:/onlinebookstore/booksList";
	}

}
