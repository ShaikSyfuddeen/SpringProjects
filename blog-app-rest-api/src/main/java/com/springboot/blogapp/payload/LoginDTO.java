package com.springboot.blogapp.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
	
	private String usernameOrEmail;
	private String password;
	
	
	public LoginDTO() {
	}
	
	public LoginDTO(String usernameOrEmail, String password) {
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

}
