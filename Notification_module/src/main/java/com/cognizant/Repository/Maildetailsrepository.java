package com.cognizant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Entity.Maildetails;

@Repository
public interface Maildetailsrepository extends JpaRepository<Maildetails, Long>{

}
