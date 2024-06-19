package com.springProject.onlineBookStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springProject.onlineBookStore.dao.BookStoreDAO;
import com.springProject.onlineBookStore.entity.Book;

@Service
public class BookStoreServiceImpl implements BookStoreService {
	
	private BookStoreDAO bookStoreDAO;
	
	public BookStoreServiceImpl(BookStoreDAO theBookStoreRepository) {
		bookStoreDAO = theBookStoreRepository;
	}

	@Override
	public List<Book> findAll() {
		return bookStoreDAO.getAllBooks();
	}

	@Override
	public Book find(int theId) {
		Book theBook = null;
		theBook = bookStoreDAO.getBookById(theId);
		
		if(theBook == null) {
			throw new RuntimeException("No Book foung with the given id");
		}
		return theBook;
	}

	@Override
	public void save(Book theBook) {
		bookStoreDAO.saveBook(theBook);
	}
	
	@Override
	public void update(Book theBook) {
		bookStoreDAO.updateBookDetails(theBook);
	}

	@Override
	public void deleteById(int theId) {
		bookStoreDAO.deleteBook(theId);
	}

	@Override
	public int deleteAll() {
		int rows = bookStoreDAO.deleteAllBooks();
		return rows;
	}

}
