package com.cognizant.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {

	private String name;
	private String category;
	private String location;
	private LocalDate date;
	private Long organizerId; // just pass organizer's userID
	private int ticketAvailability;
}
