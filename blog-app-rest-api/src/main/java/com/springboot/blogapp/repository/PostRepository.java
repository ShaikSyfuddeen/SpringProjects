package com.springboot.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogapp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
