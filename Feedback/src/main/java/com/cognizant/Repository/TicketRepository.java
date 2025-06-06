package com.cognizant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Entity.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

	boolean existsByUser_UserIdAndEvent_EventId(long userId, long eventId);

}
