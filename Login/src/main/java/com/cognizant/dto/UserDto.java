package com.cognizant.dto;





import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	//private Long userId;
	
	@NotBlank(message="Name is mandatory")
    private String name;
	@Email(message="Invalid email format")
	@NotBlank(message="Email is mandatory")
    private String email;
	@Size(min =6, message="Password must be at least 6 characters")
    private String password;
	@Pattern(regexp ="\\d{10}", message ="Contact number should be 10 digits")
    private String contactNumber;
}
