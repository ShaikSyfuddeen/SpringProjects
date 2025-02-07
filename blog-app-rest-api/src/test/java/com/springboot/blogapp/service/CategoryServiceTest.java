package com.springboot.blogapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.blogapp.entity.Category;
import com.springboot.blogapp.payload.CategoryDTO;
import com.springboot.blogapp.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@Test
	public void getCategoryTest() {
		Category c = new Category(1l, "Test Category", "Category for testing");
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		assertEquals(c, categoryService.getCategory(1l));	
	}
	
	@Test
	public void getCategoryDTOTest() {
		Category c = new Category(1l, "Test Category", "Category for testing");
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		assertNotEquals(null, categoryService.getCategoryDTO(1l));
	}
	
	@Test
	public void getAllCategoriesTest() {
		Category c1 = new Category(1l, "Test Category-1", "Category-1 for testing");
		Category c2 = new Category(2l, "Test Category-2", "Category-2 for testing");
		when(categoryRepository.findAll()).thenReturn(Stream.of(c1, c2).collect(Collectors.toList()));
		assertEquals(2, categoryService.getAllCategories().size());
	}
	
	@Test
	public void updateCategoryTest() {
		CategoryDTO dto = new CategoryDTO(1l, "Test Category", "Category for testing");
		Category c = modelMapper.map(dto, Category.class);
		Category c1 = new Category(1l, "Test Category-1", "Category-1 for testing");
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c1));
		when(categoryRepository.save(c1)).thenReturn(c);
		assertNotEquals(null, categoryService.updateCategory(dto, 1l));
	}
	
	@Test
	public void deleteCategoryTest() {
		Category c = new Category(1l, "Test Category", "Category for testing");
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		categoryService.deleteCategory(1l);
		verify(categoryRepository, times(1)).delete(c);
	}
	
}
