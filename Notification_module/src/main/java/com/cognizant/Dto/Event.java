package com.cognizant.Dto;
 
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

		private Long eventId;
 
	    private String name;
	    private String category;
	    private String location;
	    private LocalDate date;
	    private User user;
	    private int ticketAvailability;
 
}