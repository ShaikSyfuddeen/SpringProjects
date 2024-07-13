package com.springProject.ToDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springProject.ToDoList.entity.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>{

}
