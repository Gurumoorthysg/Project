package com.cognizant.service;


import java.util.List;

import com.cognizant.dto.TicketDto;
import com.cognizant.dto.TicketOutputDto;
import com.cognizant.exception.InvalidIdException;



public interface TicketServiceInterface {

	public TicketOutputDto book(TicketDto dto)throws InvalidIdException;
	public List<TicketOutputDto> viewAllTickets();
	public List<TicketOutputDto> viewTicketsByEventId(Long eventId);
	public List<TicketOutputDto> viewTicketsByUserId(Long userId);
	public String ticketCancel(Long ticketId) throws InvalidIdException;
	
	
}
