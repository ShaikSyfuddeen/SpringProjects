package com.springboot.blogapp.service;

import com.springboot.blogapp.payload.PostDTO;

public interface PostService {

	PostDTO createPost(PostDTO postDTO);
}
