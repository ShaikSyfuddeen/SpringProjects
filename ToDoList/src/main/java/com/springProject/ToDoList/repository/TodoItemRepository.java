package com.springProject.ToDoList.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springProject.ToDoList.entity.TodoItem;
import com.springProject.ToDoList.entity.User;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>{
	
	public Page<TodoItem> findAllByUser(User user, Pageable pageable);
	
	public Optional<TodoItem> findByIdAndUser(Long id, User user);
	
}
