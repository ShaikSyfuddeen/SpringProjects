package com.springProject.onlineBookStore.dao;

import java.util.List;

import com.springProject.onlineBookStore.entity.Book;

public interface BookStoreDAO{
	
	public Book getBookById(int id);
	
	public List<Book> getAllBooks();
	
	public void saveBook(Book theBook);
	
	public void updateBookDetails(Book theBook);
	
	public void deleteBook(int id);
	
	public int deleteAllBooks();
}
