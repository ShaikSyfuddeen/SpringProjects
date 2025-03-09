package com.springboot.blogapp.service;


import com.springboot.blogapp.entity.Comment;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.BlogAPIException;
import com.springboot.blogapp.payload.CommentDTO;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void createCommentTest_returnsDTO() {
        Long postId = 1L;
        Post post = new Post(postId, "Test Post", "Test post description", "Test post content");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        CommentDTO commentDTO = new CommentDTO();
        Comment comment = new Comment();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        assertEquals(CommentDTO.class, commentService.createComment(postId, commentDTO).getClass());
    }

    @Test
    public void getCommentsByPostIdTest_Success() {
        Long postId = 1L;
        List<Comment> comments = new ArrayList();
        when(commentRepository.findByPostId(postId)).thenReturn(comments);
        assertEquals(0, commentService.getCommentsByPostId(postId).size());
    }

    @Test
    public void getCommentByIdTest_Success() {
        Long postId = 1L;
        Long commentId = 1L;
        Post post = new Post(postId, "Test Post", "Test post description", "Test post content");
        Comment comment = new Comment();
        comment.setPost(post);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        assertEquals(CommentDTO.class, commentService.getCommentById(postId, commentId).getClass());
    }

    @Test
    public void getCommentByIdTest_throwsBlogAPIException() {
        Long postId = 1L;
        Long commentId = 1L;
        Post post1 = new Post(postId, "Test Post", "Test post1 description", "Test post1 content");
        Post post2 = new Post(2L, "Test Post", "Test post2 description", "Test post2 content");
        Comment comment = new Comment();
        comment.setPost(post2);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post1));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        assertThrows(BlogAPIException.class, () -> commentService.getCommentById(postId, commentId).getClass());
    }

    @Test
    public void updateCommentTest() {
        Long postId = 1L;
        Long commentId = 1L;
        Post post = new Post(postId, "Test Post", "Test post description", "Test post content");
        Comment comment = new Comment();
        comment.setPost(post);
        CommentDTO commentRequest = new CommentDTO();
        Comment updatedComment = new Comment();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);
        assertEquals(CommentDTO.class, commentService.updateComment(postId, commentId, commentRequest).getClass());
    }

    @Test
    public void deleteCommentTest() {
        Long postId = 1L;
        Long commentId = 1L;
        Post post = new Post(postId, "Test Post", "Test post description", "Test post content");
        Comment comment = new Comment();
        comment.setPost(post);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        commentService.deleteComment(postId, commentId);
        verify(commentRepository, times(1)).delete(any(Comment.class));
    }
}
