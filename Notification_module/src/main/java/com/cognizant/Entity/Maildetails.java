package com.cognizant.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Maildetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notifiId;
	private String emailId;
	private String body;
	private LocalDateTime senttime;


}
