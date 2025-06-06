package com.cognizant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
