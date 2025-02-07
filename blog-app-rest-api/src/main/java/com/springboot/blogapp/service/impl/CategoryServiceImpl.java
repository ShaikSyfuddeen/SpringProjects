package com.springboot.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.entity.Category;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.payload.CategoryDTO;
import com.springboot.blogapp.repository.CategoryRepository;
import com.springboot.blogapp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDTO addCategory(CategoryDTO categoryDTO) {
		
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryDTO(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
								.orElseThrow(() ->new ResourceNotFoundException("Category", "id", categoryId));
		return modelMapper.map(category, CategoryDTO.class);
	}
	
	@Override
	public Category getCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() ->new ResourceNotFoundException("Category", "id", categoryId));
		return category;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map((category) -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		category.setName(categoryDTO.getName());
		category.setDescription(categoryDTO.getDescription());
		category.setId(categoryId);
		
		Category updatedCategory = categoryRepository.save(category);
		return modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		categoryRepository.delete(category);
	}

}
