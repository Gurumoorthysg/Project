package com.cognizant.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Feedback {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long feedbackId;

	    @ManyToOne
	    @JoinColumn(name = "event_id")
	    @JsonBackReference
	    private Event event;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    @JsonBackReference
	    private User user;

	    private int rating;
	    private String comments;
	    private String submittedTimestamp;
}
