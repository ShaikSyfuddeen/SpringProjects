package com.springboot.blogapp.service;

import java.util.List;

import com.springboot.blogapp.payload.PostDTO;
import com.springboot.blogapp.payload.PostResponse;

public interface PostService {

	PostDTO createPost(PostDTO postDTO);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDTO getPostById(long id);
	
	PostDTO upadteDTO(PostDTO postDTO, long id);
	
	void deletePostById(long id);
	
	List<PostDTO> getPostsByCategory(Long categoryId);
}
