package com.movierator.movierator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movierator.movierator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.active = 1")
	List<User> findAllActive(); 
	
	Optional<User> findUserByLogin(String login); 
}
