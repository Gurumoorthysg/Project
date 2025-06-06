
package com.cognizant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cognizant.dto.EventDto;
import com.cognizant.dto.EventRequestDto;
import com.cognizant.entity.Event;
import com.cognizant.entity.User;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.repository.EventRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.EventServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventApplicationTests{

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    // 1. Test: Add Event
    @Test
    public void testAddEvent() {
        EventRequestDto eventDto = new EventRequestDto();
        eventDto.setName("Music Show");
        eventDto.setCategory("Concert");
        eventDto.setLocation("Chennai");
        eventDto.setDate(LocalDate.now());
        eventDto.setOrganizerId(1L);

        User user = new User();
        user.setUserId(1L);

        Event savedEvent = new Event();
        savedEvent.setName("Music Show");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        EventDto result = eventService.addEvent(eventDto);

        assertEquals("Music Show", result.getName());
    }

    // 2. Test: Get All Events
    @Test
    public void testViewAllEvent() {
        Event event1 = new Event();
        event1.setName("Event 1");

        Event event2 = new Event();
        event2.setName("Event 2");

        when(eventRepository.findAll()).thenReturn(List.of(event1, event2));

        List<EventDto> result = eventService.viewAllEvent();

        assertEquals(2, result.size());
    }

    // 3. Test: View Event By ID
    @Test
    public void testViewEventById() throws InvalidIdException {
        Event event = new Event();
        event.setEventId(10L);
        event.setName("Tech Summit");

        when(eventRepository.findById(10L)).thenReturn(Optional.of(event));

        EventDto result = eventService.viewEventById(10L);

        assertEquals("Tech Summit", result.getName());
    }

    // 4. Test: Check Event Ownership
    @Test
    public void testIsEventOwnedByUser() {
        Event event = new Event();
        User user = new User();
        user.setEmail("guru@gmail.com");
        event.setOrganizerId(user);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        boolean isOwner = eventService.isEventOwnedByUser(1L, "guru@gmail.com");

        assertTrue(isOwner);
    }
}