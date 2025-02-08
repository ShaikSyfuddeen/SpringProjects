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
	
	private Category getSampleCategory(long id) {
		Category c = new Category(id, "Test Category-"+id, "Category-" + id + " for testing");
		return c;
	}
	
	@Test
	public void getCategoryDTOTest() {
		Category c = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		assertNotEquals(null, categoryService.getCategoryDTO(1l));
	}
	
	@Test
	public void getAllCategoriesTest() {
		Category c1 = getSampleCategory(1l);
		Category c2 = getSampleCategory(2l);
		when(categoryRepository.findAll()).thenReturn(Stream.of(c1, c2).collect(Collectors.toList()));
		assertEquals(2, categoryService.getAllCategories().size());
	}
	
	@Test
	public void updateCategoryTest() {
		CategoryDTO dto = new CategoryDTO(1l, "Test Category", "Category for testing");
		Category c = modelMapper.map(dto, Category.class);
		Category c1 = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c1));
		when(categoryRepository.save(c1)).thenReturn(c);
		assertNotEquals(null, categoryService.updateCategory(dto, 1l));
	}
	
	@Test
	public void deleteCategoryTest() {
		Category c = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		categoryService.deleteCategory(1l);
		verify(categoryRepository, times(1)).delete(c);
	}
	
}
