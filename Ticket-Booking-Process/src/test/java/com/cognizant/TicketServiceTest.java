package com.cognizant;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.config.EventClient;
import com.cognizant.config.NotificationClient;
import com.cognizant.dto.TicketDto;
import com.cognizant.dto.TicketOutputDto;
import com.cognizant.entity.Event;
import com.cognizant.entity.Ticket;
import com.cognizant.entity.User;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.exception.TicketOverFlowException;
import com.cognizant.mapper.UserMapper;
import com.cognizant.repository.EventRepository;
import com.cognizant.repository.TicketBookingRepository;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.TicketService;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketBookingRepository ticketBookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private EventClient eventClient;

    @InjectMocks
    private TicketService ticketService;

    private User user;
    private Event event;
    private Ticket ticket;
    private TicketDto ticketDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        event = new Event();
        event.setEventId(1L);

        ticket = new Ticket();
        ticket.setBookingId(1L);
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setNoOfTickets(2);
        ticket.setBookingDate(LocalDate.now());
        ticket.setStatus("Success");

        ticketDto = new TicketDto();
        ticketDto.setUserId(1L);
        ticketDto.setEventId(1L);
        ticketDto.setNoOfTickets(2);
    }

    @Test
    void testBookTicket() throws InvalidIdException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventClient.getTicketAvailability(1L)).thenReturn(10);
        when(ticketBookingRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketOutputDto result = ticketService.book(ticketDto);

        verify(eventClient).updateTicketAvailability(1L, 2);
        verify(notificationClient).ticketBookedNotification(any());
        assertEquals(UserMapper.ticketToOutputDto(ticket), result);
    }

    @Test
    void testBookTicketInvalidUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> ticketService.book(ticketDto));
    }

    @Test
    void testBookTicketInvalidEventId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> ticketService.book(ticketDto));
    }

    @Test
    void testBookTicketOverflow() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventClient.getTicketAvailability(1L)).thenReturn(1);

        assertThrows(TicketOverFlowException.class, () -> ticketService.book(ticketDto));
    }

    @Test
    void testViewAllTickets() {
        when(ticketBookingRepository.findAll()).thenReturn(Arrays.asList(ticket));

        assertEquals(Arrays.asList(UserMapper.ticketToOutputDto(ticket)), ticketService.viewAllTickets());
    }

    @Test
    void testViewAllTicketsEmpty() {
        when(ticketBookingRepository.findAll()).thenReturn(Arrays.asList());

        assertThrows(InvalidIdException.class, () -> ticketService.viewAllTickets());
    }

    @Test
    void testCancelTicket() throws InvalidIdException {
        when(ticketBookingRepository.findById(1L)).thenReturn(Optional.of(ticket));

        String result = ticketService.ticketCancel(1L);

        verify(eventClient).updateCancelTickets(1L, 2);
        verify(notificationClient).ticketCancelledNotification(any());
        assertEquals("Cancellation successful", result);
    }

    @Test
    void testCancelTicketInvalidId() {
        when(ticketBookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> ticketService.ticketCancel(1L));
    }

    @Test
    void testViewTicketsByEventId() {
        when(ticketBookingRepository.findByEvent_EventId(1L)).thenReturn(Arrays.asList(ticket));

        assertEquals(Arrays.asList(UserMapper.ticketToOutputDto(ticket)), ticketService.viewTicketsByEventId(1L));
    }

    @Test
    void testViewTicketsByEventIdEmpty() {
        when(ticketBookingRepository.findByEvent_EventId(1L)).thenReturn(Arrays.asList());

        assertThrows(InvalidIdException.class, () -> ticketService.viewTicketsByEventId(1L));
    }

    @Test
    void testViewTicketsByUserId() {
        when(ticketBookingRepository.findByUser_UserId(1L)).thenReturn(Arrays.asList(ticket));

        assertEquals(Arrays.asList(UserMapper.ticketToOutputDto(ticket)), ticketService.viewTicketsByUserId(1L));
    }

    @Test
    void testViewTicketsByUserIdEmpty() {
        when(ticketBookingRepository.findByUser_UserId(1L)).thenReturn(Arrays.asList());

        assertThrows(InvalidIdException.class, () -> ticketService.viewTicketsByUserId(1L));
    }
}
