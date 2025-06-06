package com.cognizant.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
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
@Table(name="ticket")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="eventId")
	private Event event;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	
	private int noOfTickets;
	private LocalDate bookingDate;
	private String status="Success";
	
	
	public Ticket(Event event, User user,int noOfTickets, LocalDate bookingDate) {
		this.event = event;
		this.user = user;
		this.noOfTickets=noOfTickets;
		this.bookingDate = bookingDate;
	
	}
	
	
	
	
	
}
