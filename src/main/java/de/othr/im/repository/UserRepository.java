package de.othr.im.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.im.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
