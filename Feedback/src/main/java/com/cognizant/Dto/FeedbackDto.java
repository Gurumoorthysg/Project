package com.cognizant.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackDto {

	@Positive(message = "Event ID must be positive number")
	private long eventId;

	@Positive(message = "User ID must be positive number")
	private long userId;

	@Min(value = 1, message = "Rating must be at least 1")
	@Max(value = 5, message = "Rating must not exceed 5")
	private int rating;

	@NotBlank(message = "Comment must not be blank")
	@Size(min = 10, message = "Comment must be greater then 10 characters")
	private String comment;
}
