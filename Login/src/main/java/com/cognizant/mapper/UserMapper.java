package com.cognizant.mapper;

import com.cognizant.dto.UserDisplayDto;
import com.cognizant.dto.UserDto;
import com.cognizant.entity.User;

public class UserMapper {
	
	public static UserDisplayDto mapToUserDisplayDto(User user) {
		return new UserDisplayDto(
				user.getUserId(),
				user.getName(),
				user.getEmail(),
				user.getContactNumber()
				
				);
				
	}
	
	public static User mapToUser(UserDto userDto) {
		return new User(
				
				userDto.getName(),
				userDto.getEmail(),
				userDto.getPassword(),
				userDto.getContactNumber()
				);
		
				
	}

}
