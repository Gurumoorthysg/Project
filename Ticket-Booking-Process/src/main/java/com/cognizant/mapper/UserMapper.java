package com.cognizant.mapper;
 
import com.cognizant.dto.TicketDto;
import com.cognizant.dto.TicketOutputDto;
import com.cognizant.entity.Event;
import com.cognizant.entity.Ticket;
import com.cognizant.entity.User;
 
public class UserMapper {
 
	public static TicketOutputDto ticketToOutputDto(Ticket ticket) {
		TicketOutputDto dto= new TicketOutputDto(ticket.getBookingId(),ticket.getEvent().getEventId(),ticket.getEvent().getName(),ticket.getEvent().getDate(),ticket.getEvent().getLocation(),ticket.getUser().getName(),ticket.getUser().getEmail(),ticket.getNoOfTickets(),ticket.getBookingDate(),ticket.getStatus());
		return dto;
	}
//	public static TicketDto
	public static Ticket dtoToTicket(TicketDto dto,User user,Event event) {
		Ticket ticket= new Ticket(event,user,dto.getNoOfTickets(),dto.getBookingDate());
		return ticket;
	}

}