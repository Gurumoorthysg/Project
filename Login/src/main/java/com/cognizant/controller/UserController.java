package com.cognizant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.UserDisplayDto;
import com.cognizant.dto.UserDto;
import com.cognizant.entity.User;
import com.cognizant.eventapp.LoginRequest;
import com.cognizant.service.UserService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "User sign-up and login operations")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDto) {
    	logger.info("Received request to register user: {}", userDto.getEmail());
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
    	logger.info("Login attempt for email: {}", loginRequest.getEmail());
//        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    	
    	
        logger.info("Login successful for user ID");
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    
    @GetMapping("/getuserbyid/{id}")
    @Operation(summary = "Get user profile")
    public ResponseEntity<UserDisplayDto> getUser(@Valid @PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        return (ResponseEntity<UserDisplayDto>) ResponseEntity.ok(userService.getUserById(id));
    }

//    @PreAuthorize("@eventSecurity.isOwner(#id,authentication)")
    @PutMapping("/updatebyid/{id}")
    @Operation(summary = "Update user profile")
    public ResponseEntity<UserDisplayDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDisplayDto userDto) {
        logger.info("Updating user with ID: {}", id);
        return (ResponseEntity<UserDisplayDto>) ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/deleteuserbyid/{id}")
    @Operation(summary = "Delete user account")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        logger.info("User deleted with ID: {}", id);
        return ResponseEntity.ok("User deleted successfully.");
    }
    
    @GetMapping("/getalluser")
    @Operation(summary = "list all users")
    public ResponseEntity<List<User>> listAllUsers(){
    	return ResponseEntity.ok(userService.getAllUser());
    }
}
