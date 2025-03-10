package com.springboot.blogapp.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {
	
	private String name;
	private String username;
	private String email;
	private String password;
	
	public RegisterDTO() {
	}
	
	public RegisterDTO(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

}
