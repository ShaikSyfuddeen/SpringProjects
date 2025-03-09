package com.springboot.blogapp.controller;

import com.springboot.blogapp.payload.CommentDTO;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.security.JwtTokenProvider;
import com.springboot.blogapp.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void createCommentTest() {
        Long postId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        Mockito.when(commentService.createComment(postId, commentDTO)).thenReturn(commentDTO);

        ResponseEntity<CommentDTO> response = commentController.createComment(postId, commentDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CommentDTO.class, response.getBody().getClass());
        Mockito.verify(commentService, Mockito.times(1)).createComment(postId, commentDTO);
    }

    @Test
    public void getCommentsByPostIdTest() {
        Long postId = 1L;
        List<CommentDTO> commentDTOList = new ArrayList<>();
        Mockito.when(commentService.getCommentsByPostId(postId)).thenReturn(commentDTOList);
        List<CommentDTO> response = commentController.getCommentsByPostId(postId);
        assertEquals(0, response.size());
    }

    @Test
    public void getCommentByIdTest() {
        Long postId = 1L;
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        Mockito.when(commentService.getCommentById(postId, commentId)).thenReturn(commentDTO);

        ResponseEntity<CommentDTO> response = commentController.getCommentById(postId, commentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CommentDTO.class, response.getBody().getClass());
        Mockito.verify(commentService, Mockito.times(1)).getCommentById(postId, commentId);
    }

    @Test
    public void updateCommentTest() {
        Long postId = 1L;
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        Mockito.when(commentService.updateComment(postId, commentId, commentDTO)).thenReturn(commentDTO);

        ResponseEntity<CommentDTO> response = commentController.updateComment(postId, commentId, commentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CommentDTO.class, response.getBody().getClass());
        Mockito.verify(commentService, Mockito.times(1)).updateComment(postId, commentId, commentDTO);
    }

    @Test
    public void deleteCommentTest() {
        Long postId = 1L;
        Long commentId = 1L;
        ResponseEntity<String> response = commentController.deleteComment(postId, commentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment deleted successfully", response.getBody());
        Mockito.verify(commentService, Mockito.times(1)).deleteComment(postId, commentId);
    }
}
