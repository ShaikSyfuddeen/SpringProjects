package com.springboot.blogapp.service.impl;

import org.springframework.stereotype.Service;

import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.payload.PostDTO;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		
		// convert DTO to entity
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		
		Post newPost = postRepository.save(post);
		
		// convert entity to DTO
		PostDTO postResponse = new PostDTO();
		postResponse.setId(newPost.getId());
		postResponse.setTitle(newPost.getTitle());
		postResponse.setDescription(newPost.getDescription());
		postResponse.setContent(newPost.getContent());
		
		return postResponse;
	}

}
