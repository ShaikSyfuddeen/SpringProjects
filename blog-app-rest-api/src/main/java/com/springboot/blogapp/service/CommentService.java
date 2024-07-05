package com.springboot.blogapp.service;

import java.util.List;

import com.springboot.blogapp.payload.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(Long postId, CommentDTO commentDTO);
	
	List<CommentDTO> getCommentsByPostId(Long postId);
	
	CommentDTO getCommentById(Long postId, Long commentId);
	
	CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest);
	
	void deleteComment(Long postId, Long commentId);
}
