package com.cognizant;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.Config.TicketClient;
import com.cognizant.Dto.Event;
import com.cognizant.Dto.Ticket;
import com.cognizant.Dto.User;
import com.cognizant.Repository.Maildetailsrepository;
import com.cognizant.Repository.NotificationRepository;
import com.cognizant.Service.NotificationServiceImpl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

public class NotificationModuleApplicationTests {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private Maildetailsrepository maildetailsRepo;

    @Mock
    private NotificationRepository notificationRepo;

    @Mock
    private TicketClient ticketClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendBookedEmail() {
        User user = new User();
        user.setUserId(1L);
        user.setName("Guru");
        user.setEmail("guru@example.com");

        Event event = new Event();
        event.setEventId(101L);
        event.setName("Spring Boot Event");
        event.setDate(LocalDate.of(2025, 6, 1));

        notificationService.sendTicketBookedEmail(user, event);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendCancelledEmail() {
        User user = new User();
        user.setUserId(2L);
        user.setName("Guru");
        user.setEmail("guru@example.com");

        Event event = new Event();
        event.setEventId(102L);
        event.setName("Java Meetup");
        event.setDate(LocalDate.of(2025, 6, 10));

        notificationService.sendTicketCancelledEmail(user, event);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testReminderEmail_NoTickets() {
        when(ticketClient.viewAllTicketinsticketform()).thenReturn(null);

        String result = notificationService.eventRemainderEmail(LocalDate.now());

        assertEquals("No ticket found", result);
    }

    @Test
    public void testReminderEmail_WithValidTicket() {
        Event event = new Event();
        event.setEventId(103L);
        event.setName("Microservices Conf");
        event.setDate(LocalDate.now().plusDays(3));  // Reminder should be today

        User user = new User();
        user.setUserId(3L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        Ticket ticket = new Ticket();
        ticket.setBookingId(1L);
        ticket.setStatus("success");
        ticket.setEvent(event);
        ticket.setUser(user);

        when(ticketClient.viewAllTicketinsticketform()).thenReturn(Arrays.asList(ticket));

        String result = notificationService.eventRemainderEmail(event.getDate());

        assertEquals("Mail sent successfully to the users", result);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}

