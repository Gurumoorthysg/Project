package com.cognizant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class EventSecurity {

	@Autowired
	EventService eventService;
	
	public boolean isEventOwner(Long eventId,Authentication authentication) {
		String userEmail=authentication.getName();
		return eventService.isEventOwnedByUser(eventId,userEmail);
		
	}
}
