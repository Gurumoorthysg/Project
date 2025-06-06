package com.cognizant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
