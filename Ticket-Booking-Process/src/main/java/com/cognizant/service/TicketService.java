package com.cognizant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.config.EventClient;
import com.cognizant.config.NotificationClient;
import com.cognizant.config.NotificationRequestDTO;
import com.cognizant.dto.TicketDto;
import com.cognizant.dto.TicketOutputDto;
import com.cognizant.entity.Event;
import com.cognizant.entity.Ticket;
import com.cognizant.entity.User;
import com.cognizant.exception.InvalidCancelRequestException;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.exception.TicketOverFlowException;
import com.cognizant.mapper.UserMapper;
import com.cognizant.repository.EventRepository;
import com.cognizant.repository.TicketBookingRepository;
import com.cognizant.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketService implements TicketServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    TicketBookingRepository repo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    EventRepository eventRepo;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private EventClient eventClient;

    @Override
    public TicketOutputDto book(TicketDto dto) throws InvalidIdException {
        logger.info("Booking ticket for user ID: {} and event ID: {}", dto.getUserId(), dto.getEventId());
        Long userid = dto.getUserId();
        Optional<User> userop = userRepo.findById(userid);
        if (userop.isEmpty()) {
            logger.error("Invalid user ID: {}", userid);
            throw new InvalidIdException("The user id does not exist");
        }

        User user = userop.get();

        Long eventid = dto.getEventId();
        Optional<Event> eventop = eventRepo.findById(eventid);
        if (eventop.isEmpty()) {
            logger.error("Invalid event ID: {}", eventid);
            throw new InvalidIdException("The event id does not exist");
        }

        Event event = eventop.get();
        
        if(dto.getBookingDate().isAfter(event.getDate())) {
        	logger.error(" booking date for the completed event: {}", dto.getBookingDate());
        	throw new InvalidIdException("Sorry the event date is completed.You cannot book now!!");
        }

        int availability = eventClient.getTicketAvailability(eventid);

        if (availability < dto.getNoOfTickets()) {
            logger.error("Ticket overflow: requested {}, available {}", dto.getNoOfTickets(), availability);
            throw new TicketOverFlowException("No:of tickets is more than available tickets");
        }

        eventClient.updateTicketAvailability(eventid, dto.getNoOfTickets());

        Ticket savedTicket = repo.save(UserMapper.dtoToTicket(dto, user, event));

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setUser(user);
        request.setEvent(event);

        notificationClient.ticketBookedNotification(request);

        logger.info("Ticket booked successfully for user ID: {} and event ID: {}", dto.getUserId(), dto.getEventId());
        return UserMapper.ticketToOutputDto(savedTicket);
    }

    @Override
    public List<TicketOutputDto> viewAllTickets() {
        logger.info("Viewing all tickets");
        List<Ticket> tickets = repo.findAll();
        if (tickets.isEmpty()) {
            logger.warn("No tickets available in the database");
            throw new InvalidIdException("There is no tickets available in the database");
        }
        return tickets.stream().map(ti -> (UserMapper.ticketToOutputDto(ti))).collect(Collectors.toList());
    }

    @Override
    public String ticketCancel(Long bookingId) throws InvalidIdException {
        logger.info("Cancelling ticket with ID: {}", bookingId);
        Optional<Ticket> op = repo.findById(bookingId);
        if (op.isEmpty()) {
            logger.error("Invalid ticket ID: {}", bookingId);
            throw new InvalidIdException("Ticket Id not found");
        }

        Ticket ticket = op.get();
        if (ticket.getStatus().equals("Cancelled")) {
            logger.warn("Ticket already cancelled with ID: {}", bookingId);
            throw new InvalidCancelRequestException("Ticket is already cancelled");
        }
        ticket.setStatus("Cancelled");
        repo.save(ticket);
        eventClient.updateCancelTickets(ticket.getEvent().getEventId(), ticket.getNoOfTickets());

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setUser(ticket.getUser());
        request.setEvent(ticket.getEvent());
        notificationClient.ticketCancelledNotification(request);

        logger.info("Cancellation successful for ticket ID: {}", bookingId);
        return "Cancellation successful";
    }

    @Override
    public List<TicketOutputDto> viewTicketsByEventId(Long eventId) {
        logger.info("Viewing tickets by event ID: {}", eventId);
        List<Ticket> tickets = repo.findByEvent_EventId(eventId);
        if (tickets.isEmpty()) {
            logger.warn("No tickets found for event ID: {}", eventId);
            throw new InvalidIdException("No tickets found for event ID: " + eventId);
        }

        return tickets.stream().map(ti -> (UserMapper.ticketToOutputDto(ti))).collect(Collectors.toList());
    }

    @Override
    public List<TicketOutputDto> viewTicketsByUserId(Long userId) {
        logger.info("Viewing tickets by user ID: {}", userId);
        List<Ticket> tickets = repo.findByUser_UserId(userId);
        if (tickets.isEmpty()) {
            logger.warn("No tickets found for user ID: {}", userId);
            throw new InvalidIdException("No tickets found for user ID: " + userId);
        }

        return tickets.stream().map(ti -> (UserMapper.ticketToOutputDto(ti))).collect(Collectors.toList());
    }

    public List<Ticket> viewAllTicketsInTicketform() {
        logger.info("Viewing all tickets in ticket form");
        return repo.findAll();
    }

    public boolean isTicketOwnedByUser(Long ticketId, String userEmail) {
        logger.info("Checking if ticket ID: {} is owned by user email: {}", ticketId, userEmail);
        Optional<Ticket> ticketOpt = repo.findById(ticketId);
        return ticketOpt.isPresent() && ticketOpt.get().getUser().getEmail().equals(userEmail);
    }
}
