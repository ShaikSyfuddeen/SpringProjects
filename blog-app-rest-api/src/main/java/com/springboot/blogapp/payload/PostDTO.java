package com.springboot.blogapp.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Schema(
		description = "PostDTO Model information"
		)
@AllArgsConstructor
@NoArgsConstructor
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

	public PostDTO(long id, String title, String description, String content, Long categoryId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.categoryId = categoryId;
	}

}
