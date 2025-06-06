package com.cognizant.Dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket{

	private Long bookingId;

	private Event event;

	private User user;
	
	private int noOfTickets;
	private LocalDate bookingDate;
	private String status;

}
