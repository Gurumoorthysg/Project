package com.cognizant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	

}
