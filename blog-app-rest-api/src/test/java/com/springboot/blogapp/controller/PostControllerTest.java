package com.springboot.blogapp.controller;

import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.payload.JWTAuthResponse;
import com.springboot.blogapp.payload.PostDTO;
import com.springboot.blogapp.payload.PostResponse;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.security.JwtTokenProvider;
import com.springboot.blogapp.service.PostService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostController postController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostService postService;

    @Test
    public void createPostTest() {
        long id=1L;

        Post post = new Post(id, "Test Post", "Test post description", "Test post content");
        PostDTO dto = new PostDTO(id, "Test Post" , "Test description", "Test Content", id);
        Mockito.when(postService.getPostById(id)).thenReturn(dto);

        ResponseEntity<PostDTO> response = postController.createPost(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(postService, times(1)).createPost(dto);
    }

    @Test
    public void getAllPostsTest() {
        when(postService.getAllPosts(1, 10, "asc", "id")).thenReturn(new PostResponse());

        PostResponse response = postController.getAllPosts(1, 10, "asc", "id");

        assertNotNull(response);
        verify(postService, times(1)).getAllPosts(1, 10, "asc", "id");
    }

    @Test
    public void getPostByIdTest() {
        PostDTO dto = new PostDTO(1L, "Test Post" , "Test description", "Test Content", 1L);
        when(postService.getPostById(1L)).thenReturn(dto);

        ResponseEntity<PostDTO> response = postController.getPostById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService, times(1)).getPostById(1L);
    }

    @Test
    public void updatePostTest() {
        PostDTO dto = new PostDTO(1L, "Test Post" , "Test description", "Test Content", 1L);
        when(postService.upadteDTO(dto, 1L)).thenReturn(dto);

        ResponseEntity<PostDTO> response = postController.updatePost(dto, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
       verify(postService, times(1)).upadteDTO(dto, 1L);
    }

    @Test
    public void deletePostTest() {
        Long id=1L;
        ResponseEntity<String> response = postController.deletePost(id);
        assertEquals("Post entity deleted successfully.", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService, times(1)).deletePostById(id);
    }

    @Test
    public void getPostsByCategoryTest() {
        Long id=1L;
        List<PostDTO> posts = new ArrayList<>();
        when(postService.getPostsByCategory(id)).thenReturn(posts);

        ResponseEntity<List<PostDTO>> response = postController.getPostsByCategory(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(postService, times(1)).getPostsByCategory(id);
    }
}
