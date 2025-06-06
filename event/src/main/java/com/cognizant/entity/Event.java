package com.cognizant.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;
	private String name;
	private String category;
	private String location;
	private LocalDate date;
	
	@ManyToOne
    @JoinColumn(name = "organizerId", nullable = false)
    private User organizerId;
	private int ticketAvailability;
	
	public Event(String name, String category, String location, LocalDate date, User organizerId, int ticketAvailability) {
		super();
		this.name = name;
		this.category = category;
		this.location = location;
		this.date = date;
		this.organizerId = organizerId;
		this.ticketAvailability = ticketAvailability;
	}
	

	
	
}


