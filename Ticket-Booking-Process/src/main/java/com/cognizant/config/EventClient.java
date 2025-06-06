package com.cognizant.config;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.cognizant.dto.UserDto;


@FeignClient(name = "Event-Service",

configuration = FeignClientConfig.class)
public interface EventClient {
	
	@GetMapping("/api/event/{eventId}/availability")
	public int getTicketAvailability(@PathVariable Long eventId);

	@PostMapping("/api/event/{eventId}/availability")
	public void updateTicketAvailability(@PathVariable Long eventId, @RequestParam int tickets);

	@PostMapping("/api/event/{eventId}/updateCancelTicketAvailability")
	public void updateCancelTickets(@PathVariable Long eventId, @RequestParam int tickets);

}
