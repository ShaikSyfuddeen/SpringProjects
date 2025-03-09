package com.springboot.blogapp.security;

import com.springboot.blogapp.entity.Role;
import com.springboot.blogapp.entity.User;
import com.springboot.blogapp.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void loadUserByUsernameTest_Success() {
        Role role = new Role(1L, "TestRole");
        Set<Role> roles = Set.of(role);
        User user = new User(1L, "TestUser", "TestUser", "testuser@email.com", "testPassword", roles);

        Mockito.when(userRepository.findByUsernameOrEmail("TestUser", "TestUser")).thenReturn(Optional.of(user));

        Assertions.assertNotNull(customUserDetailsService.loadUserByUsername("TestUser"));
    }

    @Test
    public void loadUserByUsernameTest_ReturnsException() {
        Role role = new Role(1L, "TestRole");
        Set<Role> roles = Set.of(role);
        User user = new User(1L, "TestUser", "TestUser", "testuser@email.com", "testPassword", roles);

        Mockito.when(userRepository.findByUsernameOrEmail("TestUser", "testuser@email.com")).thenReturn(Optional.of(user));

        Assertions.assertThrows(UsernameNotFoundException.class, () ->customUserDetailsService.loadUserByUsername("TestUser"));
    }
}
