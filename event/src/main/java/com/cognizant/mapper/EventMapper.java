package com.cognizant.mapper;

import com.cognizant.dto.EventDto;
import com.cognizant.dto.EventRequestDto;
import com.cognizant.entity.Event;
import com.cognizant.entity.User;

public class EventMapper {

	public static EventDto mapToEventDto(Event event) {
		EventDto eventDto = new EventDto(
				
				event.getEventId(),
				event.getName(),
				event.getCategory(),
				event.getLocation(),
				event.getDate(),
				event.getOrganizerId(),
				event.getTicketAvailability()
				);
				return eventDto;
	}
	
	public static Event mapToEntity(EventRequestDto dto, User organizer) {
		return new Event(
			dto.getName(),
			dto.getCategory(),
			dto.getLocation(),
			dto.getDate(),
			organizer,
			dto.getTicketAvailability()
		);
	}
	public static Event mapToEvent(EventDto eventDto) {
		Event event = new Event (
				
				
				eventDto.getName(),
				eventDto.getCategory(),
				eventDto.getLocation(),
				eventDto.getDate(),
				eventDto.getOrganizerId(),
				eventDto.getTicketAvailability()
				);
		return event;
				
	}
}
