package com.cognizant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entity.Ticket;

@Repository
public interface TicketBookingRepository extends JpaRepository<Ticket, Long>{


	public List<Ticket> findByEvent_EventId(Long eventId);

	public List<Ticket> findByUser_UserId(Long userId);
}
