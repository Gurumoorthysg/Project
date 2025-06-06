package com.cognizant.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.Dto.FeedbackDto;
import com.cognizant.Entity.Feedback;
import com.cognizant.Service.FeedbackServiceList;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackServiceList servicelist;

	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@PostMapping("/submitfeedback")
	@Operation(summary = "Submit feedback for the event")
	public ResponseEntity<String> submitfeedback(@Valid @RequestBody FeedbackDto dto) {
		logger.info("API Call: Submit Feedback - userId={}, eventId={}", dto.getUserId(), dto.getEventId());
		servicelist.submitfeedback(dto);
		logger.info("API Response: Feedback submitted successfully");
		return ResponseEntity.ok("Feedback submitted successfully");
	}

	@GetMapping("/getfeedbackbyeventid/{eventId}")
	@Operation(summary = "Get all feedback for the particular event")
	public ResponseEntity<List<Feedback>> getfeedbackbyevent(@PathVariable Long eventId) {
		logger.info("API Call: Get Feedback by Event ID {}", eventId);
		List<Feedback> feedbackList = servicelist.getfeedbackbyevent(eventId);
		logger.info("API Response: Returned {} feedback items", feedbackList.size());
		return ResponseEntity.ok(feedbackList);
	}

	@GetMapping("/getaveragebyeventid/{eventId}")
	@Operation(summary = "Get average rating for the particular event")
	public ResponseEntity<String> getaveragebyeventid(@PathVariable Long eventId) {
		logger.info("API Call: Get Average Feedback for Event ID {}", eventId);
		Double average = servicelist.getaveragebyeventid(eventId);
		logger.info("API Response: Average rating is {}", average);
		return ResponseEntity.ok(" Average rating for the Event " + eventId + " is " + average);
	}

}
