package com.cognizant.config;

import com.cognizant.entity.Event;
import com.cognizant.entity.User;

public class NotificationRequestDTO {
	private User user;
	private Event event;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

}
