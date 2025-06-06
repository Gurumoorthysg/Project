package com.cognizant.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

	
//	private Long bookingId;
	
	private Long eventId;
	
	private Long userId;
	
	private int noOfTickets;
	
	private LocalDate bookingDate;


}
