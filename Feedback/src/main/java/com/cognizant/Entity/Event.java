package com.cognizant.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Event {
	 
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long eventId;

	    private String name;
	    private String category;
	    private String location;
	    private LocalDate date;

	    @ManyToOne
	    @JoinColumn(name = "organizer_id")
	    private User organizer;
	    
	    private Long ticketAvailability;

}
