package com.springProject.onlineBookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springProject.onlineBookStore.dao.BookStoreRepository;
import com.springProject.onlineBookStore.entity.Book;

@Service
public class BookStoreServiceImpl implements BookStoreService {
	
	private BookStoreRepository bookStoreRepository;
	
	@Autowired
	public BookStoreServiceImpl(BookStoreRepository theBookStoreRepository) {
		bookStoreRepository = theBookStoreRepository;
	}

	@Override
	public List<Book> findAll() {
		return bookStoreRepository.findAll();
	}

	@Override
	public Book find(int theId) {
		Book theBook = null;
		theBook = bookStoreRepository.getById(theId);
		
		if(theBook == null) {
			throw new RuntimeException("No Book foung with the given id");
		}
		return theBook;
	}

	@Override
	public void save(Book theBook) {
		bookStoreRepository.save(theBook);
	}

	@Override
	public void deleteById(int theId) {
		bookStoreRepository.deleteById(theId);
	}

}
