package com.springboot.blogapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogapp.payload.CategoryDTO;
import com.springboot.blogapp.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(
		name = "CRUD REST APIs for Category Resource"
		)
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	// add category REST API
	@Operation(
			summary = "Create Category REST API",
			description = "Create Category REST API is used to save Category into database"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
			)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){
		CategoryDTO savedCategoryDTO = categoryService.addCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(savedCategoryDTO, HttpStatus.CREATED);
	}
	
	// get category REST API
	@Operation(
			summary = "Get Category by Id REST API",
			description = "Get Category by Id REST API is used to get single Category from database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") Long categoryId){
		CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	}
	
	// get all categories REST API
	@Operation(
			summary = "Get all Categories REST API",
			description = "Get all Categories REST API is used to fetch all the Categories from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	// update category REST API
	@Operation(
			summary = "Update Category by Id REST API",
			description = "Update Category by Id REST API is used to update a single Category in the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("categoryId") Long categoryId){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, categoryId));
	}
	
	// delete category REST API
	@Operation(
			summary = "Delete Category by Id REST API",
			description = "Delete Category by Id REST API is used to delete a single Category from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Success"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully");
	}

}
