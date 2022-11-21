package com.movierator.movierator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.movierator.movierator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public static Integer PROFILE_ADMIN = 1;
	public static Integer PROFILE_CONTRIBUTOR = 2;
	
	Optional<User> findUserByLogin(String login); 
}
