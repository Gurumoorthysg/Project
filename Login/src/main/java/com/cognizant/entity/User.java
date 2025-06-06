package com.cognizant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
 @Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;

	    private String name;
	    
	    private String roles="USER";

	    @Column(unique = true, nullable = false)
	    private String email;

	    private String password;

	    private String contactNumber;

		public User(String name, String email, String password, String contactNumber) {
			super();
			this.name = name;
			this.email = email;
			this.password = password;
			this.contactNumber = contactNumber;
		}
	    
	    

}
