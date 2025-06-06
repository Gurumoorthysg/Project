package com.cognizant.Controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cognizant.Dto.NotificationRequestDTO;
import com.cognizant.Service.NotificationServiceList;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/notification")
public class Notificationcontroller {

	private static final Logger logger = LoggerFactory.getLogger(Notificationcontroller.class);

	@Autowired
	private NotificationServiceList servicelist;

	@Hidden
	@PostMapping("/booked")
	public String TicketBookedNotification(@RequestBody NotificationRequestDTO requestdto) {

		logger.info("Ticket booked notification requested for userId={}, eventId={}", requestdto.getUser().getUserId(),
				requestdto.getEvent().getEventId());

		servicelist.sendTicketBookedEmail(requestdto.getUser(), requestdto.getEvent());
		return "Booked mail sent successfully";
	}

	@Hidden
	@PostMapping("/cancelled")
	public String TicketCancelledNotification(@RequestBody NotificationRequestDTO requestdto) {

		logger.info("Ticket cancellation notification requested for userId={}, eventId={}",
				requestdto.getUser().getUserId(), requestdto.getEvent().getEventId());

		servicelist.sendTicketCancelledEmail(requestdto.getUser(), requestdto.getEvent());
		return "Cancelled mail sent successfully";
	}

	@GetMapping("/remainder/{date}")
	@Operation(summary = "Send remainder email to the users by event date")
	public String eventRemainderEmail(@PathVariable LocalDate date) {

		logger.info("Event reminder requested for date {}", date);

		String message = servicelist.eventRemainderEmail(date);

		logger.info("Reminder process result: {}", message);

		return message;
	}
}
