package com.springProject.ToDoList.Security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TodoSecurityConfig {
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource theDataSource) {
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(theDataSource);
		
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT user_id, password, active from users where user_id=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select user_id,role from roles where user_id=?");
		
		return jdbcUserDetailsManager;
	}

	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
	
		http.authorizeHttpRequests(configurer -> 
		configurer
				.anyRequest().authenticated()
			)
		.formLogin(form ->
	 		form
	 			.loginPage("/TodoApp/showMyLoginPage")
	 			.loginProcessingUrl("/authenticateTheUser")
	 			.permitAll()
			)
		.logout(logout -> 
				logout.permitAll()
			)
		.exceptionHandling(configurer -> 
			configurer.accessDeniedPage("/TodoApp/access-denied")
		);
		return http.build();
	}
}
