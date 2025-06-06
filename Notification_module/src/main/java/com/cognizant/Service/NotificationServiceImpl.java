package com.cognizant.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cognizant.Config.TicketClient;
import com.cognizant.Dto.Event;
import com.cognizant.Dto.Ticket;
import com.cognizant.Dto.User;
import com.cognizant.Entity.Maildetails;
import com.cognizant.Entity.Notification;
import com.cognizant.Repository.Maildetailsrepository;
import com.cognizant.Repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationServiceList {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Maildetailsrepository maildetailsrepos;

	@Autowired
	private NotificationRepository notificationrepos;

	@Autowired
	private TicketClient ticketclient;

	@Override
	public void sendTicketBookedEmail(User user, Event event) {

		logger.info("Sending ticket booked email to user: {}", user.getEmail());

		String subject = "Ticket Booked: " + event.getName();
		String body = "Dear " + user.getName() + ",\n\n" + "Your ticket for the event " + event.getName()
				+ " has been successfully booked.\n" + "Event Date: " + event.getDate() + "\n\n"
				+ "Thanks,\nEvent Management Team";
		sendNotification(user.getEmail(), subject, body);
		try {
			saveNotificationDetails(user.getUserId(), event.getEventId(), body, subject, user.getEmail());
		} catch (Exception e) {
			logger.error("Error saving booked mail notification: {}", e.getMessage());
		}
	}

	@Override
	public void sendTicketCancelledEmail(User user, Event event) {

		logger.info("Sending ticket cancelled email to user: {}", user.getEmail());

		String subject = "Ticket Cancelled: " + event.getName();
		String body = "Dear " + user.getName() + ",\n\n" + "Your ticket for the event " + event.getName()
				+ " has been cancelled.\n" + "Event Date: " + event.getDate() + "\n\n"
				+ "Thanks,\nEvent Management Team";
		sendNotification(user.getEmail(), subject, body);
		try {
			saveNotificationDetails(user.getUserId(), event.getEventId(), body, subject, user.getEmail());
		} catch (Exception e) {
			logger.error("Error saving cancelled mail notification: {}", e.getMessage());
		}
	}

	public void sendNotification(String toEmail, String subject, String body) {

		logger.info("Sending email to: {}, subject: {}", toEmail, subject);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gurusg202@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		sender.send(message);

		logger.info("Email sent to {}", toEmail);
	}

	public void saveNotificationDetails(long userId, long eventId, String message, String subject, String emailId) {

		logger.info("Saving notification and mail details for userId={}, eventId={}", userId, eventId);

		Maildetails maildetails = new Maildetails();
		maildetails.setEmailId(emailId);
		maildetails.setBody(message);
		maildetails.setSenttime(LocalDateTime.now());

		Maildetails savemail = maildetailsrepos.save(maildetails);

		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setEventId(eventId);
		notification.setMessage(message);
		notification.setSentTimeStamp(LocalDateTime.now());
		notification.setMaildetails(savemail);

		notificationrepos.save(notification);

		logger.info("Notification saved for userId={}, eventId={}", userId, eventId);
	}

	@Override
	public String eventRemainderEmail(LocalDate date) {

		logger.info("Fetching tickets for event reminder on date: {}", date);

		List<Ticket> ticketlist = ticketclient.viewAllTicketinsticketform();

		if (ticketlist == null || ticketlist.isEmpty()) {
			logger.warn("No tickets found for sending reminders");
			return "No ticket found";
		}

		boolean reminderSent = false;

		for(Ticket tickets : ticketlist) {
			if (!"success".equalsIgnoreCase(tickets.getStatus())) {
				logger.debug("Skipping ticket {} due to non-success status", tickets.getBookingId());
				continue;
			}

			Event event = tickets.getEvent();
			User user = tickets.getUser();

			if (event == null || user == null) {
				logger.error("Missing event or user for ticket ID: {}", tickets.getBookingId());
				continue;
			}

			LocalDate eventdate = event.getDate();
			LocalDate reminderDate = eventdate.minusDays(3);

			if (eventdate.isEqual(date) && LocalDate.now().isEqual(reminderDate)) {
				logger.info("Sending reminder for event '{}' to user '{}'", event.getName(), user.getEmail());
				String subject = "Reminder: " + event.getName();
				String message = "Dear " + user.getName() + ",\n\n" + "This is a reminder for the event: "
						+ event.getName() + "\nScheduled Date: " + event.getDate()
						+ "\n\nThanks,\nEvent Management Team";

				sendNotification(user.getEmail(), subject, message);
				saveNotificationDetails(user.getUserId(), event.getEventId(), message, subject, user.getEmail());
				reminderSent = true;
			}
		}

		if (reminderSent) {
			logger.info("Reminder emails sent for events on {}", date);
			return "Mail sent successfully to the users";
		} else {
			logger.info("No events scheduled for reminders on {}", date);
			return "Event not schedule in that day or not before 3 days";
		}
	}
}
