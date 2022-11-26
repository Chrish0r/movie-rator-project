package com.movierator.movierator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movierator.movierator.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
//	@Query("SELECT a FROM " +  Admin.class.getSimpleName() + " a WHERE a.user.id = :userId")
	@Query("select a from Admin a where a.user.id = :userId")
	Optional<Admin> findAdminByUserId(Long userId); 
}
