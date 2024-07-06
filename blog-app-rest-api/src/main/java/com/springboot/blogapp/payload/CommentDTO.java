package com.springboot.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
