package com.springboot.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.entity.Category;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.payload.PostDTO;
import com.springboot.blogapp.payload.PostResponse;
import com.springboot.blogapp.repository.CategoryRepository;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		
		Category category = categoryRepository.findById(postDTO.getCategoryId())
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
		
		// convert DTO to entity
		Post post = mapToEntity(postDTO);
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		
		// convert entity to DTO

        return mapToDTO(newPost);
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
					:Sort.by(sortBy).descending();
		// create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		// get content from page object
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	@Override
	public PostDTO getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
	}
	
	@Override
	public PostDTO upadteDTO(PostDTO postDTO, long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		
		Category category = categoryRepository.findById(postDTO.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
		
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		
		return mapToDTO(updatedPost);
	}
	

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		if(post != null) {
			postRepository.delete(post);
		}
	}

	@Override
	public List<PostDTO> getPostsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		return posts.stream().map(post -> this.mapToDTO(post)).collect(Collectors.toList());
	}
	private PostDTO mapToDTO(Post post) {
        return mapper.map(post, PostDTO.class);
	}

	private Post mapToEntity(PostDTO postDTO) {
        return mapper.map(postDTO, Post.class);
	}
}
