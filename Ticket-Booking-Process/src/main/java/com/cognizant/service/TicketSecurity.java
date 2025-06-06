package com.cognizant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TicketSecurity {

	@Autowired
	TicketService ticketService;
	
	public boolean isTicketOwner(Long ticketId,Authentication authentication) {
		String userEmail=authentication.getName();
		return ticketService.isTicketOwnedByUser(ticketId,userEmail);
		
	}
}
