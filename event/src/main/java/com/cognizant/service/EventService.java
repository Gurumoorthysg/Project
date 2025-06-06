package com.cognizant.service;

import java.time.LocalDate;
import java.util.List;

import com.cognizant.dto.EventDto;
import com.cognizant.dto.EventRequestDto;
import com.cognizant.exception.InvalidCategoryException;
import com.cognizant.exception.InvalidDateException;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.exception.InvalidLocationException;

public interface EventService {

	public int getTicketAvailability(Long eventId);

	public void updateTicketAvailability(Long eventId, int tickets);

	public void updateCancelTickets(Long eventId, int tickets);

	public EventDto addEvent(EventRequestDto eventDto);

	public EventDto viewEventById(Long id) throws InvalidIdException;

	public List<EventDto> viewAllEvent();

	public EventDto updateEventById(Long id, EventDto eventDto) throws InvalidIdException;

	public String deleteEventById(Long id) throws InvalidIdException;

	public List<EventDto> viewByCategory(String category) throws InvalidCategoryException;

	public List<EventDto> viewByDate(LocalDate date) throws InvalidDateException;

	public List<EventDto> viewByLocation(String location) throws InvalidLocationException;

	public boolean isEventOwnedByUser(Long eventId, String userEmail);

}
