package com.cognizant.Config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cognizant.Dto.Ticket;



@FeignClient(name="Ticket-Service" ,configuration = FeignClientConfig.class)
public interface TicketClient {
	
	@GetMapping("/api/ticket/viewallticketsinformofticket")
	public List<Ticket> viewAllTicketinsticketform();
	

}
