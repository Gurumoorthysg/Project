package com.cognizant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.dto.UserDisplayDto;
import com.cognizant.dto.UserDto;

import com.cognizant.entity.User;
import com.cognizant.exception.InvalidException;
import com.cognizant.mapper.UserMapper;
import com.cognizant.repository.UserRepository;

import lombok.Data;

@Service
@Data
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private
	JwtService jwtService;

	  @Autowired
	    private BCryptPasswordEncoder passwordEncoder;

	    public User registerUser(UserDto userDto) {
	    	logger.info("Registering new user: {}", userDto.getEmail());
	        User user = UserMapper.mapToUser(userDto);
	        Optional<User> existingUser = getUserRepository().findByEmail(user.getEmail());
	        if (existingUser.isPresent()) {
	            logger.warn("Registration failed. Email already exists: {}", user.getEmail());
	            throw new InvalidException("Email already registered.");
	        }
	        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
	        User saveduser= getUserRepository().save(user);
	        logger.info("User registered successfully with ID: {}", user.getUserId());
	        return saveduser;

	    }

	    public String login(String email, String rawPassword) {
	        logger.info("Authenticating user with email: {}", email);
	        User user = getUserRepository().findByEmail(email)
	                .orElseThrow(() -> logAndThrow(email));

	        if (!getPasswordEncoder().matches(rawPassword, user.getPassword())) {
	            logger.warn("Login failed. Password mismatch for email: {}", email);
	            throw new InvalidException("Invalid email or password.");
	        }
	        String token=getJwtService().generateToken(email, user.getRoles());
	        
	        logger.info("User authenticated successfully: {}", user.getUserId());
	        return token;
	    }
    
    private InvalidException logAndThrow(String email) {
        logger.warn("Login failed. Email not found: {}", email);
			return new InvalidException("Invalid email or password");
		}

	public UserDisplayDto getUserById(Long id) {
        return getUserRepository().findById(id)
                .map(UserMapper::mapToUserDisplayDto)
                .orElseThrow(() -> logAndThrow(id));
    }

    public UserDisplayDto updateUser(Long id, UserDisplayDto userDto) {
        logger.info("Updating user ID: {}", id);
        User existingUser = getUserRepository().findById(id)
                .orElseThrow(() -> logAndThrow(id));

        existingUser.setName(userDto.getName());
        existingUser.setContactNumber(userDto.getContactNumber());
        logger.info("User updated successfully: {}", userDto.getUserId());
        return UserMapper.mapToUserDisplayDto(getUserRepository().save(existingUser));
    }
    
    private InvalidException logAndThrow(Long id) {
        logger.warn("Failed to fetch. User not found: {}", id);
			return new InvalidException("User not found");
		}

    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        getUserRepository().deleteById(id);
        logger.info("User deleted successfully: {}", id);
    }
    public List<User> getAllUser() {
		return getUserRepository().findAll();
	}
    
    
public boolean isOwnedByUser(Long eventId, String userEmail) {
		
		Optional<User> eventOpt=getUserRepository().findById(eventId);
		return eventOpt.isPresent()&&eventOpt.get().equals(userEmail);
	}

public BCryptPasswordEncoder getPasswordEncoder() {
	return passwordEncoder;
}

public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
	this.passwordEncoder = passwordEncoder;
}

public JwtService getJwtService() {
	return jwtService;
}

public void setJwtService(JwtService jwtService) {
	this.jwtService = jwtService;
}

public UserRepository getUserRepository() {
	return userRepository;
}

public void setUserRepository(UserRepository userRepository) {
	this.userRepository = userRepository;
}
}
