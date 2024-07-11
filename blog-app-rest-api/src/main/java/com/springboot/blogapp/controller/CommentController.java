package com.springboot.blogapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogapp.payload.CommentDTO;
import com.springboot.blogapp.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
@Tag(
		name = "CRUD REST APIs for Comment Resource"
		)
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Operation(
			summary = "Create Comment REST API",
			description = "Create Comment REST API is used to save Comment to a particular post into database"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
			)
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId, @Valid @RequestBody CommentDTO commentDTO){
		return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
	}
	
	@Operation(
			summary = "Get all Comments by post id REST API",
			description = "Get all Comments by post id REST API is used to fetch all the comments that belong to a particular post from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDTO> getCommentsByPostId(@PathVariable("postId") long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@Operation(
			summary = "Get Comment by comment Id REST API",
			description = "Get Comment by comment Id REST API is used to get single Comment from database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
		
		CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDTO, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Update Comment by Id REST API",
			description = "Update Comment by Id REST API is used to update a single Comment in the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @Valid @RequestBody CommentDTO commentDTO){
		CommentDTO updatedCommentDTO = commentService.updateComment(postId, commentId, commentDTO);
		return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete Comment by Id REST API",
			description = "Delete Comment by Id REST API is used to delete a single Comment from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
	}

}
