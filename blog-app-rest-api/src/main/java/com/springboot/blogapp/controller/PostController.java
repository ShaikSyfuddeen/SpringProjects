package com.springboot.blogapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogapp.payload.PostDTO;
import com.springboot.blogapp.payload.PostResponse;
import com.springboot.blogapp.service.PostService;
import com.springboot.blogapp.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
		name = "CRUD REST APIs for Post Resource"
		)
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	// create blog post
	@Operation(
			summary = "Create Post REST API",
			description = "Create Post REST API is used to save post into database"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
			)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	// get all posts
	@Operation(
			summary = "Get all Posts REST API",
			description = "Get all Posts REST API is used to fetch all the posts from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo", defaultValue=AppConstants.DEFAULT_PAGE_NO, required=false) int pageNo,
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false) int pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir
			){
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	// get post by id
	@Operation(
			summary = "Get Post by Id REST API",
			description = "Get Post by Id REST API is used to get single post from database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable("id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	// update post
	@Operation(
			summary = "Update Post by Id REST API",
			description = "Update Post by Id REST API is used to update a single post in the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable("id") long id){
		return new ResponseEntity<>(postService.upadteDTO(postDTO, id), HttpStatus.OK);
	}
	
	// delete post
	@Operation(
			summary = "Delete Post by Id REST API",
			description = "Delete Post by Id REST API is used to delete a single post from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id){
		
		postService.deletePostById(id);
		
		return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
	}
	
	// get posts by category REST API
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("categoryId") Long categoryId){
		return new ResponseEntity<List<PostDTO>>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
	}
}
