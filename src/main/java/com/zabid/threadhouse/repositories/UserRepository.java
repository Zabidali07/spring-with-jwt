package com.zabid.threadhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zabid.threadhouse.models.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	@Query("select u from Users u where u.emailId = :emailId and u.emailId IS NOT NULL")
	Users findByEmailId(String emailId);
	
	@Query("select u from Users u where u.username = :username and u.username IS NOT NULL")
	Users findByUsername(String username);

}
