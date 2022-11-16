package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * This class is representing the entitiy {@link User} with its various attributes.
 * 
 * @author Przemyslaw Christof Gadek
 *
 */

@Entity
@Table(name = User.TABLE)
public class User {
	
	public static final String TABLE = "users";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotEmpty(message = "User name is mandatory")
	String username;

	@NotEmpty(message = "Email is mandatory")
	String email;
	
	@NotEmpty(message = "Password is mandatory")
	@Size(min = 4, message = "Password must contain at least 4 characters")
	String password;
	
	public User() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
