package com.springboot.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogapp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	public List<Post> findByCategoryId(Long categoryId);
}
