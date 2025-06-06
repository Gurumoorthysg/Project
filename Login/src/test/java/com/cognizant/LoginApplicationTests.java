package com.cognizant;


import com.cognizant.entity.User;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.JwtService;
import com.cognizant.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginApplicationTests {
	
	User user = new User();
	
	@BeforeEach
	public void start() {
		
	}

    @Test
    public void testLoginUsingMockitoOnly() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtService jwtService = mock(JwtService.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        UserService userService = new UserService();
        userService.setUserRepository(userRepository);
        userService.setJwtService(jwtService);
        userService.setPasswordEncoder(passwordEncoder);

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPass");
        mockUser.setRoles("USER");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("pass123", "encodedPass")).thenReturn(true);
        when(jwtService.generateToken("test@example.com", "USER")).thenReturn("mock-token");

        // Act
        String result = userService.login("test@example.com", "pass123");

        // Assert
        assertEquals("mock-token", result);
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("pass123", "encodedPass");
        verify(jwtService).generateToken("test@example.com", "USER");
    }
}

