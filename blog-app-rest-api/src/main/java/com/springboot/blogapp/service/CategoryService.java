package com.springboot.blogapp.service;

import java.util.List;

import com.springboot.blogapp.payload.CategoryDTO;

public interface CategoryService {

	public CategoryDTO addCategory(CategoryDTO categoryDTO);
	
	public CategoryDTO getCategory(Long categoryId);
	
	public List<CategoryDTO> getAllCategories();
	
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
	
	public void deleteCategory(Long categoryId);
}
