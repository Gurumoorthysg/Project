package com.cognizant.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="Notification-Service")
public interface NotificationClient {
	
	@PostMapping("/api/notification/booked")
	String ticketBookedNotification(@RequestBody NotificationRequestDTO request);
	
	@PostMapping("/api/notification/cancelled")
	String ticketCancelledNotification(@RequestBody NotificationRequestDTO request);

}
