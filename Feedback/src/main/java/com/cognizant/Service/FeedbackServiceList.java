package com.cognizant.Service;

import java.util.List;

import com.cognizant.Dto.FeedbackDto;
import com.cognizant.Entity.Feedback;


public interface FeedbackServiceList {

	void submitfeedback(FeedbackDto dto);

	List<Feedback> getfeedbackbyevent(Long eventId);
	
	double getaveragebyeventid(Long eventId);

}
