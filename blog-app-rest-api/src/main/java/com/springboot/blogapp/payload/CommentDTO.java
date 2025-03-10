package com.springboot.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {

	private long id;
	
	@NotEmpty(message="Name should not be null or empty")
	private String name;
	
	@NotEmpty(message="Email should not be null or empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=10, message="Message should be minimum 10 charecters")
	private String body;

}
