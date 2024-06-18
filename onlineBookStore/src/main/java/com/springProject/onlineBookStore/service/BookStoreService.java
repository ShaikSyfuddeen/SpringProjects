package com.springProject.onlineBookStore.service;

import java.util.List;

import com.springProject.onlineBookStore.entity.Book;

public interface BookStoreService {

	public List<Book> findAll();
	
	public Book find(int theId);
	
	public void save(Book theBook);
	
	public void deleteById(int theId);
}
