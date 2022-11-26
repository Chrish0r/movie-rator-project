package com.movierator.movierator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movierator.movierator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
//	public static Integer PROFILE_ADMIN = 1; // required? If so, then for my other roles as well
	
	Optional<User> findUserByLogin(String login); 
}
