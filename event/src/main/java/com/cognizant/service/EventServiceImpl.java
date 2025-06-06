package com.cognizant.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.dto.EventDto;
import com.cognizant.dto.EventRequestDto;
import com.cognizant.entity.Event;
import com.cognizant.entity.User;
import com.cognizant.exception.InvalidCategoryException;
import com.cognizant.exception.InvalidDateException;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.exception.InvalidLocationException;
import com.cognizant.exception.InvalidUserException;
import com.cognizant.mapper.EventMapper;
import com.cognizant.repository.EventRepository;
import com.cognizant.repository.UserRepository;


@Service
public class EventServiceImpl implements EventService {

	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	EventRepository eventRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public EventDto addEvent(EventRequestDto eventDto) {
		logger.info("Adding new event: {}", eventDto.getName());
		Optional<User> user = userRepository.findById(eventDto.getOrganizerId());
		if (user.isEmpty()) {
			throw new InvalidUserException("User not found");
		}
		User org = user.get();
		Event event = EventMapper.mapToEntity(eventDto, org);

		Event savedEvent = eventRepository.save(event);
		EventDto savedEventDto = EventMapper.mapToEventDto(savedEvent);
		logger.info("Event saved with ID: {}", savedEvent.getEventId());
		return savedEventDto;
	}

	@Override
	public List<EventDto> viewAllEvent() {
		logger.info("Retrieving all events");
		List<Event> event = eventRepository.findAll();
		return event.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
	}

	@Override
	public EventDto updateEventById(Long id, EventDto eventDto) throws InvalidIdException {
		logger.info("Updating event by ID: {}", id);
		Optional<Event> opt = eventRepository.findById(id);
		if (opt.isEmpty()) {
			logger.warn("No event found with ID: {}", id);
			throw new InvalidIdException("No Event is present in this id");
		}
		Event event = opt.get();
		event.setName(eventDto.getName());
		event.setCategory(eventDto.getCategory());
		event.setDate(eventDto.getDate());
		event.setLocation(eventDto.getLocation());
		event.setOrganizerId(eventDto.getOrganizerId());
		event.setTicketAvailability(eventDto.getTicketAvailability());
		Event update = eventRepository.save(event);
		logger.info("Event updated successfully for ID: {}", id);
		return EventMapper.mapToEventDto(update);
	}
 

	@Override
	public String deleteEventById(Long id) throws InvalidIdException {
		// TODO Auto-generated method stub
		Optional<Event> opt = eventRepository.findById(id);
		if (opt.isEmpty()) {
			logger.warn("Delete failed - no event with ID: {}", id);
			throw new InvalidIdException("No Id is present to Delete");
		}
		eventRepository.deleteById(id);
		logger.info("Event deleted successfully with ID: {}", id);
		return "Event Id " + id + " is deleted";

	}

	@Override
	public List<EventDto> viewByCategory(String category) throws InvalidCategoryException {
		logger.info("Retrieving events for category: {}", category);
		List<Event> event = eventRepository.findByCategory(category);
		if (event.isEmpty()) {
			logger.warn("No events found in category: {}", category);
			throw new InvalidCategoryException("No Event are Present in this Category");
		}
		return event.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
	}

	@Override
	public List<EventDto> viewByDate(LocalDate date) throws InvalidDateException {
		logger.info("Retrieving events for date: {}", date);
		List<Event> event = eventRepository.findByDate(date);
		if (event.isEmpty()) {
			logger.warn("No events found on date: {}", date);
			throw new InvalidDateException("No Event are Present in that Date");
		}
		return event.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
	}

	@Override
	public List<EventDto> viewByLocation(String location) throws InvalidLocationException {
		logger.info("Retrieving events for location: {}", location);
		List<Event> event = eventRepository.findByLocation(location);
		if (event.isEmpty()) {
			logger.warn("No events found in location: {}", location);
			throw new InvalidLocationException("No Event are Present in this Location");
		}
		return event.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
	}

	@Override
	public EventDto viewEventById(Long id) throws InvalidIdException {
		logger.info("Fetching event details for ID: {}", id);
		Optional<Event> opt = eventRepository.findById(id);
		if (opt.isPresent()) {
			Event event = opt.get();
			return EventMapper.mapToEventDto(event);
		} else {
			logger.warn("Event not found for ID: {}", id);
			throw new InvalidIdException("No Event Id is present to Display");
		}

	}

	@Override
	public int getTicketAvailability(Long eventId) {

		Optional<Event> opt = eventRepository.findById(eventId);

		Event event = opt.get();
		return event.getTicketAvailability();

	}

	@Override
	public void updateTicketAvailability(Long eventId, int tickets) {

		Optional<Event> opt = eventRepository.findById(eventId);

		Event event = opt.get();
		int newAvailability = event.getTicketAvailability() - tickets;

		event.setTicketAvailability(newAvailability);
		eventRepository.save(event);

	}

	@Override
	public void updateCancelTickets(Long eventId, int tickets) {
		Optional<Event> opt = eventRepository.findById(eventId);

		Event event = opt.get();
		int newAvailability = event.getTicketAvailability() + tickets;

		event.setTicketAvailability(newAvailability);
		eventRepository.save(event);

	}
	
//}
	
public boolean isEventOwnedByUser(Long eventId, String userEmail) {
		
		Optional<Event> eventOpt=eventRepository.findById(eventId);
		return eventOpt.isPresent()&&eventOpt.get().getOrganizerId().getEmail().equals(userEmail);
	}

}
