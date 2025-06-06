package com.cognizant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.Dto.FeedbackDto;
import com.cognizant.Entity.Event;
import com.cognizant.Entity.Feedback;
import com.cognizant.Entity.User;
import com.cognizant.Exception.InvalidException;
import com.cognizant.Repository.EventRepository;
import com.cognizant.Repository.FeedbackRepository;
import com.cognizant.Repository.TicketRepository;
import com.cognizant.Repository.UserRepository;
import com.cognizant.Service.FeedbackServiceImpl;

public class EventmanagementprojectApplicationTests {

    @Mock
    private FeedbackRepository feedbackrepos;

    @Mock
    private TicketRepository ticketrepos;

    @Mock
    private EventRepository eventrepos;

    @Mock
    private UserRepository userrepos;

    @InjectMocks
    private FeedbackServiceImpl service;

    private FeedbackDto dto;
    private Event event;
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setEventId(1L);
        dto.setRating(4);
        dto.setComment("Great event!");

        event = new Event();
        event.setEventId(1L);
        event.setName("Sample Event");

        user = new User();
        user.setUserId(1L);
        user.setEmail("user@example.com");
    }

    @Test
    public void testSubmitFeedback_Success() throws InvalidException {
        when(eventrepos.findById(dto.getEventId())).thenReturn(Optional.of(event));
        when(userrepos.findById(dto.getUserId())).thenReturn(Optional.of(user));
        when(ticketrepos.existsByUser_UserIdAndEvent_EventId(dto.getUserId(), dto.getEventId())).thenReturn(true);

        service.submitfeedback(dto);

        verify(feedbackrepos, times(1)).save(any(Feedback.class));
    }

    @Test
    public void testGetFeedbackByEvent_Success() throws InvalidException {
        when(eventrepos.existsById(1L)).thenReturn(true);

        Feedback fb1 = new Feedback();
        fb1.setRating(4);
        fb1.setComments("Good");
        fb1.setEvent(event);
        fb1.setUser(user);

        List<Feedback> feedbackList = Arrays.asList(fb1);

        when(feedbackrepos.findAllByEvent_EventId(1L)).thenReturn(feedbackList);

        List<Feedback> result = service.getfeedbackbyevent(1L);
        assertEquals(1, result.size());
        assertEquals("Good", result.get(0).getComments());
    }

    @Test
    public void testGetAverageByEventId_Success() throws InvalidException {
        Feedback fb1 = new Feedback();
        fb1.setRating(4);
        Feedback fb2 = new Feedback();
        fb2.setRating(2);

        List<Feedback> feedbackList = Arrays.asList(fb1, fb2);

        when(feedbackrepos.findByEvent_eventId(1L)).thenReturn(feedbackList);

        double avg = service.getaveragebyeventid(1L);
        assertEquals(3.0, avg);
    }
}

