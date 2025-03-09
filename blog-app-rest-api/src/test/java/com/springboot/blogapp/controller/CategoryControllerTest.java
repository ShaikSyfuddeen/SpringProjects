package com.springboot.blogapp.controller;

import com.springboot.blogapp.payload.CategoryDTO;
import com.springboot.blogapp.repository.CategoryRepository;
import com.springboot.blogapp.security.JwtTokenProvider;
import com.springboot.blogapp.service.CategoryService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void addCategoryTest() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Test Category", "Test description");
        Mockito.when(categoryService.addCategory(Mockito.any(CategoryDTO.class))).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.addCategory(categoryDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(categoryService, Mockito.times(1)).addCategory(categoryDTO);
    }

    @Test
    public void getCategoryTest() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, "Test Category", "Test description");
        Mockito.when(categoryService.getCategoryDTO(categoryId)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.getCategory(categoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CategoryDTO.class, response.getBody().getClass());
        Mockito.verify(categoryService, Mockito.times(1)).getCategoryDTO(categoryId);
    }

    @Test
    public void getAllCategoriesTest() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        Mockito.verify(categoryService, Mockito.times(1)).getAllCategories();
    }

    @Test
    public void updateCategoryTest() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, "Test Category", "Test description");
        Mockito.when(categoryService.updateCategory(categoryDTO, categoryId)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.updateCategory(categoryDTO, categoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CategoryDTO.class, response.getBody().getClass());
        Mockito.verify(categoryService, Mockito.times(1)).updateCategory(categoryDTO, categoryId);
    }

    @Test
    public void deleteCategory() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, "Test Category", "Test description");

        ResponseEntity<String> response = categoryController.deleteCategory(categoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        Mockito.verify(categoryService, Mockito.times(1)).deleteCategory(categoryId);
    }
}
