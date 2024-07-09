package com.springboot.blogapp.service;

import com.springboot.blogapp.payload.CategoryDTO;

public interface CategoryService {

	public CategoryDTO addCategory(CategoryDTO categoryDTO);
	
	public CategoryDTO getCategory(Long categoryId);
}
