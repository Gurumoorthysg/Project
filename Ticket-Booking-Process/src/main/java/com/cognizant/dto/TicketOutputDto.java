package com.cognizant.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketOutputDto {

	private Long bookingId;

	private Long eventId;

	private String eventName;

	private LocalDate eventDate;

	private String location;

	private String userName;
	
	private String email;

	private int noOfTickets;

	private LocalDate bookingDate;

	private String status;

}
