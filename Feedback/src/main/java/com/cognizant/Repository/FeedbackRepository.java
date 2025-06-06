package com.cognizant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findAllByEvent_EventId(Long eventId);

	List<Feedback> findByEvent_eventId(Long eventId);

}
