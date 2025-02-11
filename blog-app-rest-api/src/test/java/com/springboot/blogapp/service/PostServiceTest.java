package com.springboot.blogapp.service;

import com.springboot.blogapp.entity.Category;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.payload.PostDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.springboot.blogapp.repository.CategoryRepository;
import com.springboot.blogapp.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void createPostTest() {
        PostDTO postDTO = new PostDTO();
        long id = 1l;
        postDTO.setTitle("Test Post");
        postDTO.setDescription("Test Post For Testing");
        postDTO.setContent("Post content");
        postDTO.setCategoryId(id);

        Category category = new Category(id, "Test Category", "Test Category");
        Post post = modelMapper.map(postDTO, Post.class);
        post.setCategory(category);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(postRepository.save(Mockito.any())).thenReturn(post);

        assertNotEquals(null, postService.createPost(postDTO));
    }


    @Test
    public void getPostByIdTest() {
        long id = 1l;
        Post post = new Post(id, "Test Post", "Test post description", "Test post content");
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        assertNotEquals(null, postService.getPostById(id));
    }

    @Test
    public void updateDTOTest() {
        long id = 1l;
        Post post = new Post(id, "Test Post", "Test post description", "Test post content");
        Category category = new Category(id, "Test Category", "Test Category");

        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        when((categoryRepository.findById(id))).thenReturn(Optional.of(category));


        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Updated Test Post");
        postDTO.setDescription("Updated Test Post For Testing");
        postDTO.setContent("Updated Post content");
        postDTO.setCategoryId(id);

        Post updatedPost = modelMapper.map(postDTO, Post.class);
        when(postRepository.save(Mockito.any())).thenReturn(updatedPost);

        assertEquals("Updated Test Post", postService.upadteDTO(postDTO, id).getTitle());
        assertEquals("Updated Test Post For Testing", postService.upadteDTO(postDTO, id).getDescription());
        assertEquals("Updated Post content", postService.upadteDTO(postDTO, id).getContent());
    }
}
