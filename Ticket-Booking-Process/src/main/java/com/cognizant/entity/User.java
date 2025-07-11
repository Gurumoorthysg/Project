package com.cognizant.entity;
 

 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "user")
@Data
public class User{
 
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;
 
	    private String name;
	    private String email;
	    private String password;
	    private String contactNumber;
 
 
}