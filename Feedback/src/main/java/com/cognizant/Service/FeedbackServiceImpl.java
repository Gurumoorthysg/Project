package com.cognizant.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.Dto.FeedbackDto;
import com.cognizant.Entity.Event;
import com.cognizant.Entity.Feedback;
import com.cognizant.Entity.User;
import com.cognizant.Exception.InvalidException;
import com.cognizant.Repository.EventRepository;
import com.cognizant.Repository.FeedbackRepository;
import com.cognizant.Repository.TicketRepository;
import com.cognizant.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FeedbackServiceImpl implements FeedbackServiceList {

	@Autowired
	private FeedbackRepository feedbackrepos;
	@Autowired
	private TicketRepository ticketrepos;
	@Autowired
	private EventRepository eventrepos;
	@Autowired
	private UserRepository userrepos;

	private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	@Override
	public void submitfeedback(FeedbackDto dto) throws InvalidException {
		logger.info("Submitting feedback: userId={}, eventId={}", dto.getUserId(), dto.getEventId());

		Event event = eventrepos.findById(dto.getEventId()).orElseThrow(() -> {
			logger.error("Event not found with ID {}", dto.getEventId());
			return new InvalidException("Event not found");
		});

		User user = userrepos.findById(dto.getUserId()).orElseThrow(() -> {
			logger.error("User not found with ID {}", dto.getUserId());
			return new InvalidException("User not found");
		});

		boolean attended = ticketrepos.existsByUser_UserIdAndEvent_EventId(dto.getUserId(), dto.getEventId());
		if (!attended) {
			logger.warn("User ID {} did not attend Event ID {}", dto.getUserId(), dto.getEventId());
			throw new InvalidException("User not attended the event so can't submit feedback");
		}

		logger.info("Saving feedback for user {} on event {}", dto.getUserId(), dto.getEventId());
		Feedback savefeedback = new Feedback();
		savefeedback.setEvent(event);
		savefeedback.setUser(user);
		savefeedback.setRating(dto.getRating());
		savefeedback.setComments(dto.getComment());
		savefeedback.setSubmittedTimestamp(LocalDate.now().toString());
		feedbackrepos.save(savefeedback);
		logger.info("Feedback saved successfully");
	}

	@Override
	public List<Feedback> getfeedbackbyevent(Long eventId) throws InvalidException {
		logger.info("Fetching feedback for eventId {}", eventId);

		boolean eventexist = eventrepos.existsById(eventId);
		if (!eventexist) {

			logger.error("Event ID {} does not exist", eventId);
			throw new InvalidException("Event ID " + eventId + " is not found or not organized ");
		}

		List<Feedback> eventfound = feedbackrepos.findAllByEvent_EventId(eventId);
		if (eventfound.isEmpty()) {
			logger.warn("No feedback found for event ID {}", eventId);
			throw new InvalidException("No feedback for the entered event ID :" + eventId + " , Event name :"
					+ eventrepos.getById(eventId).getName());
		}

		logger.info("Found {} feedback entries for eventId {}", eventfound.size(), eventId);
		return eventfound;
	}

	@Override
	public double getaveragebyeventid(Long eventId) throws InvalidException {
		logger.info("Calculating average feedback rating for eventId {}", eventId);

		List<Feedback> feedbacklist = feedbackrepos.findByEvent_eventId(eventId);

		if (feedbacklist == null || feedbacklist.isEmpty()) {
			logger.warn("No feedback ratings available for event ID {}", eventId);
			throw new InvalidException("Entered eventId has no rating average or event not found");
		}

		double sum = 0;
		int count = 0;
		for (Feedback feedback : feedbacklist) {
			sum += feedback.getRating();
			count++;
		}

		double average = count > 0 ? sum / count : 0.0;
		logger.info("Average rating for eventId {} is {}", eventId, average);
		return average;
	}

}
