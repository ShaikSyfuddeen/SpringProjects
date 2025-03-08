package com.springboot.blogapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.springboot.blogapp.exception.ResourceNotFoundException;
import jakarta.validation.constraints.Null;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	public void addCategoryTest() {
		long id = 1l;
		CategoryDTO dto = new CategoryDTO(id, "Test Category-"+id, "Category-" + id + " for testing");
		Category category = modelMapper.map(dto, Category.class);
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
		assertEquals(CategoryDTO.class, categoryService.addCategory(dto).getClass());
	}

	@Test
	public void getCategoryDTOTest_returnsDTO() {
		Category c = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		assertEquals(CategoryDTO.class, categoryService.getCategoryDTO(1l).getClass());
	}

	@Test
	public void getCategoryDTOTest_ThrowsResourceNotfoundException() {
		when(categoryRepository.findById(1l)).thenThrow(new ResourceNotFoundException("Category", "id", 1l));
		assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryDTO(1l).getClass());
	}

	@Test
	public void getCategoryDTOTest_ThrowsNullPointerException() {
		when(categoryRepository.findById(1l)).thenThrow(new NullPointerException());
		assertThrows(NullPointerException.class, () -> categoryService.getCategoryDTO(1l).getClass());
	}
	
	@Test
	public void getAllCategoriesTest_returnsList() {
		Category c1 = getSampleCategory(1l);
		Category c2 = getSampleCategory(2l);
		when(categoryRepository.findAll()).thenReturn(Stream.of(c1, c2).collect(Collectors.toList()));
		assertEquals(2, categoryService.getAllCategories().size());
	}

	@Test
	public void getAllCategoriesTest_returnsNullPointerException() {
		Category c1 = getSampleCategory(1l);
		Category c2 = getSampleCategory(2l);
		when(categoryRepository.findAll()).thenReturn(null);
		assertThrows(NullPointerException.class, ()-> categoryService.getAllCategories().size());
	}
	
	@Test
	public void updateCategoryTest_returnsDTO() {
		CategoryDTO dto = new CategoryDTO(1L, "Test Category", "Category for testing");
		Category c = modelMapper.map(dto, Category.class);
		Category c1 = getSampleCategory(1L);
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(c1));
		when(categoryRepository.save(c1)).thenReturn(c);
		assertEquals(CategoryDTO.class, categoryService.updateCategory(dto, 1L).getClass());
	}

	@Test
	public void updateCategoryTest_returnsResourceNotfoundException() {
		CategoryDTO dto = new CategoryDTO(1l, "Test Category", "Category for testing");
		Category c = modelMapper.map(dto, Category.class);
		Category c1 = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenThrow(new ResourceNotFoundException("Category", "id", 1L));
		when(categoryRepository.save(c1)).thenReturn(c);
		assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(dto, 1l));
	}

	@Test
	public void updateCategoryTest_returnsNullPointerException() {
		CategoryDTO dto = new CategoryDTO(1l, "Test Category", "Category for testing");
		Category c = modelMapper.map(dto, Category.class);
		Category c1 = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenReturn(null);
		when(categoryRepository.save(c1)).thenReturn(c);
		assertThrows(NullPointerException.class, () -> categoryService.updateCategory(dto, 1l));
	}
	
	@Test
	public void deleteCategoryTest_returnsDTO() {
		Category c = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenReturn(Optional.of(c));
		categoryService.deleteCategory(1l);
		verify(categoryRepository, times(1)).delete(c);
	}

	@Test
	public void deleteCategoryTest_returnsResourceNotFoundException() {
		Category c = getSampleCategory(1l);
		when(categoryRepository.findById(1l)).thenThrow( new ResourceNotFoundException("Category", "id", 1L));
	}

	@Test
	public void deleteCategoryTest_returnsNullPointerException() {
		when(categoryRepository.findById(1l)).thenReturn(null);
		assertThrows(NullPointerException.class, () -> categoryService.deleteCategory(1L));
	}
	
}
