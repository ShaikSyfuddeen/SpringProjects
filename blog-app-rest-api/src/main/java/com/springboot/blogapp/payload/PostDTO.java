package com.springboot.blogapp.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(
		description = "PostDTO Model information"
		)
public class PostDTO {

	private long id;
	
	@Schema(description = "Blog post title")
	@NotEmpty
	@Size(min=2, message="Post title should have atleast 2 charecters")
	private String title;
	
	@Schema(description = "Blog post description")
	@NotEmpty
	@Size(min=10, message="Post description should have atleast 10 charecters")
	private String description;
	
	@Schema(description = "Blog post content")
	@NotEmpty
	private String content;
	
	private Set<CommentDTO> comments;
	
	@Schema(description = "Blog post category")
	private Long categoryId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
