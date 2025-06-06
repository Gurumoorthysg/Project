package com.cognizant.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.EventDto;
import com.cognizant.dto.EventRequestDto;
import com.cognizant.exception.InvalidCategoryException;
import com.cognizant.exception.InvalidDateException;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.exception.InvalidLocationException;
import com.cognizant.service.EventServiceImpl;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/event")
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

	@Autowired
	EventServiceImpl eventService;

	@PostMapping("/add")
	public ResponseEntity<EventDto> createEvent(@RequestBody EventRequestDto eventDto) {
		logger.info("Received request to create event with name: {}", eventDto.getName());
		return new ResponseEntity<EventDto>(eventService.addEvent(eventDto), HttpStatus.OK);
	}

	@GetMapping("/getAllEvent")
	public ResponseEntity<List<EventDto>> getEventById() {
		logger.info("Fetching all events");
		return new ResponseEntity<List<EventDto>>(eventService.viewAllEvent(), HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<EventDto> getEventById(@PathVariable long id) throws InvalidIdException {
		logger.info("Fetching event by ID: {}", id);
		return new ResponseEntity<EventDto>(eventService.viewEventById(id), HttpStatus.OK);
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<EventDto>> getEventsByCategory(@PathVariable String category)
			throws InvalidCategoryException {
		logger.info("Fetching events by category: {}", category);
		return new ResponseEntity<List<EventDto>>(eventService.viewByCategory(category), HttpStatus.OK);
	}

	@GetMapping("/location/{location}")
	public ResponseEntity<List<EventDto>> getEventsByLocation(@PathVariable String location)
			throws InvalidLocationException {

		logger.info("Fetching events by location: {}", location);
		return new ResponseEntity<List<EventDto>>(eventService.viewByLocation(location), HttpStatus.OK);
	}

	@GetMapping("/date/{date}")
	public ResponseEntity<List<EventDto>> getEventsByDate(@PathVariable LocalDate date) throws InvalidDateException {
		logger.info("Fetching events by date: {}", date);
		return new ResponseEntity<List<EventDto>>(eventService.viewByDate(date), HttpStatus.OK);
	}

	@PreAuthorize("@eventSecurity.isEventOwner(#id,authentication)")
	@PutMapping("/update/{id}")
	public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto)
			throws InvalidIdException {
		logger.info("Updating event with ID: {}", id);
		return new ResponseEntity<EventDto>(eventService.updateEventById(id, eventDto), HttpStatus.OK);
	}

	@PreAuthorize("@eventSecurity.isEventOwner(#id,authentication)")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long id) throws InvalidIdException {
		logger.info("Deleting event with ID: {}", id);
		return new ResponseEntity<String>(eventService.deleteEventById(id), HttpStatus.OK);

	}

	// feign client code
	@Hidden
	@GetMapping("{eventId}/availability")
	public ResponseEntity<Integer> getTicketAvailability(@PathVariable Long eventId) {

		int availableTickets = eventService.getTicketAvailability(eventId);
		return ResponseEntity.ok(availableTickets);

	}

	@Hidden
	@PostMapping("{eventId}/availability")
	public ResponseEntity<Void> updateTicketAvailability(@PathVariable Long eventId, @RequestParam int tickets) {

		eventService.updateTicketAvailability(eventId, tickets);
		return ResponseEntity.ok().build();

	}

	@Hidden
	@PostMapping("{eventId}/updateCancelTicketAvailability")
	public ResponseEntity<Void> updateCancelTickets(@PathVariable Long eventId, @RequestParam int tickets) {
		eventService.updateCancelTickets(eventId, tickets);
		return ResponseEntity.ok().build();
	}

}
