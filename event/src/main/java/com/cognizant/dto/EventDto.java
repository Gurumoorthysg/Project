package com.cognizant.dto;

import java.time.LocalDate;

import com.cognizant.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

	
	private Long eventId;
	private String name;
	private String category;
	private String location;
	private LocalDate date;
	private User  organizerId;
	private int ticketAvailability;
}
