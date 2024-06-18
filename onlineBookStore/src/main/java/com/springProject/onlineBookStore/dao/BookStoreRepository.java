package com.springProject.onlineBookStore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springProject.onlineBookStore.entity.Book;

public interface BookStoreRepository extends JpaRepository<Book, Integer>{

}
