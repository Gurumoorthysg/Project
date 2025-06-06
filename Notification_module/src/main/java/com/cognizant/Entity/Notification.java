package com.cognizant.Entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long notificationId;
	
	private long userId;
	private long eventId;
	private String message;
	private LocalDateTime sentTimeStamp;
	
	@OneToOne
	@JoinColumn(name="maildetails_id",referencedColumnName = "notifiId")
	private Maildetails maildetails;
	

}
