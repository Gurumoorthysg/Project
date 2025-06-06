package com.cognizant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.TicketDto;
import com.cognizant.dto.TicketOutputDto;
import com.cognizant.entity.Ticket;
import com.cognizant.exception.InvalidIdException;
import com.cognizant.service.TicketService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/ticket")
public class TicketBookingController {

    private static final Logger logger = LoggerFactory.getLogger(TicketBookingController.class);

    @Autowired
    TicketService service;

//    @PreAuthorize("@ticketSecurity.isTicketOwner(#id,authentication)")
    @PostMapping("/booktickets")
    public ResponseEntity<TicketOutputDto> bookTicket(@RequestBody TicketDto dto) throws InvalidIdException {
        logger.info("Booking ticket with details: {}", dto);
        return new ResponseEntity<TicketOutputDto>(service.book(dto), HttpStatus.OK);
    }

    @GetMapping("/viewalltickets")
    public ResponseEntity<List<TicketOutputDto>> viewAllTickets() {
        logger.info("Viewing all tickets");
        return new ResponseEntity<List<TicketOutputDto>>(service.viewAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/viewallticketsByEventId/{eventId}")
    public ResponseEntity<List<TicketOutputDto>> viewAllTicketsByEventId(@PathVariable Long eventId) {
        logger.info("Viewing all tickets by event ID: {}", eventId);
        return new ResponseEntity<List<TicketOutputDto>>(service.viewTicketsByEventId(eventId), HttpStatus.OK);
    }

    @GetMapping("/viewallticketsByUserId/{userId}")
    public ResponseEntity<List<TicketOutputDto>> viewAllTicketsByUserId(@PathVariable Long userId) {
        logger.info("Viewing all tickets by user ID: {}", userId);
        return new ResponseEntity<List<TicketOutputDto>>(service.viewTicketsByUserId(userId), HttpStatus.OK);
    }

    @PreAuthorize("@ticketSecurity.isTicketOwner(#id,authentication)")
    @PutMapping("/cancelticket/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long id) throws InvalidIdException {
        logger.info("Cancelling ticket with ID: {}", id);
        return new ResponseEntity<>(service.ticketCancel(id), HttpStatus.OK);
    }

    @Hidden
    @GetMapping("/viewallticketsinformofticket")
    public List<Ticket> viewAllTicketinsticketform() {
        logger.info("Viewing all tickets in ticket form");
        return service.viewAllTicketsInTicketform();
    }
}
