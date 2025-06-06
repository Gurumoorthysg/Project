package com.cognizant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
