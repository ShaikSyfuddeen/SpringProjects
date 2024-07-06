package com.springboot.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.entity.Comment;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.BlogAPIException;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.payload.CommentDTO;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

		Comment comment = mapToEntity(commentDTO);

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);
	}

	@Override
	public List<CommentDTO> getCommentsByPostId(Long postId) {

		// retrieve comments by post id
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDTO getCommentById(Long postId, Long commentId) {
		Comment comment = lookForComment(postId, commentId);
		return mapToDTO(comment);
	}

	@Override
	public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest) {

		Comment comment = lookForComment(postId, commentId);

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		
		Comment comment = lookForComment(postId, commentId);
		commentRepository.delete(comment);
	}

	/**
	 * Checks if a post with given postId exists,
	 * a comment with given commentId exists
	 * and the comment belongs to a post
	 * @param postId
	 * @param commentId
	 * @return
	 */
	private Comment lookForComment(Long postId, Long commentId) {

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to a post");
		}
		
		return comment;
	}

	private CommentDTO mapToDTO(Comment comment) {
		CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
		return commentDTO;
	}

	private Comment mapToEntity(CommentDTO commentDTO) {
		Comment comment = mapper.map(commentDTO, Comment.class);
		return comment;
	}

}
