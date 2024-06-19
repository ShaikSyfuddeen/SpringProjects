package com.springProject.onlineBookStore.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.springProject.onlineBookStore.entity.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class BookStoreDAOImpl implements BookStoreDAO {

	private EntityManager entityManager;
	
	public BookStoreDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Book getBookById(int id) {
		return entityManager.find(Book.class, id);
	}

	@Override
	public List<Book> getAllBooks() {
		
		TypedQuery<Book> theQuery = entityManager.createQuery("FROM Book", Book.class);
		return theQuery.getResultList();
	}

	@Override
	@Transactional
	public void saveBook(Book theBook) {
		if(theBook != null) {
			entityManager.persist(theBook);
		}
	}

	@Override
	@Transactional
	public void updateBookDetails(Book theBook) {
		if(theBook != null) {
			entityManager.merge(theBook);
		}
	}

	@Override
	@Transactional
	public void deleteBook(int id) {
		Book theBook = entityManager.find(Book.class, id);
		if(theBook!=null) {
			entityManager.remove(theBook);
		}
	}

	@Override
	@Transactional
	public int deleteAllBooks() {
		int rows = entityManager.createQuery("DELETE FROM Book").executeUpdate();
		return rows;
	}

}
