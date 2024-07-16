package com.springboot.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
					title = "Spring boot Blog App REST APIs",
					description = "Spring Boot Blog App REST APIs Documentation",
					version = "v1.0",
					contact = @Contact(
								name = "Syfuddeen",
								email = "syfuddeen107@gmail.com"
							),
					license = @License(
								name = "Apache 2.0"
							)
				),
		externalDocs = @ExternalDocumentation(
							description = "Spring Boot Blog App Documentation",
							url = "https://github.com/ShaikSyfuddeen/SpringProjects/tree/master/blog-app-rest-api"
						)
		)
public class BlogAppRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppRestApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
