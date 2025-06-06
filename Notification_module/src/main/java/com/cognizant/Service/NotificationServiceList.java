package com.cognizant.Service;

import java.time.LocalDate;

import com.cognizant.Dto.Event;
import com.cognizant.Dto.User;

public interface NotificationServiceList {

	void sendTicketBookedEmail(User user,Event event);
	
	void sendTicketCancelledEmail(User user, Event event);

	String eventRemainderEmail(LocalDate date);

}
