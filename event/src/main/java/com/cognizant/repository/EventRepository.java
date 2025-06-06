package com.cognizant.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findByCategory(String category);
	List<Event> findByLocation(String location);
	List<Event> findByDate(LocalDate Date);
}
