package com.movierator.movierator.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Authority.TABLE)
public class Authority implements Serializable {

	private static final long serialVersionUID = -7279536509954804901L;

	public static final String TABLE = "authorities";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	/* Causes problems with Spring Boot Security - might attempt later again */
//	@Enumerated(EnumType.STRING)
//	private UserRoleType description;

	public Authority(Long authority_id) {
		this.id = authority_id;
	}

	public Authority() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public UserRoleType getDescription() {
//		return description;
//	}
//
//	public void setDescription(UserRoleType description) {
//		this.description = description;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
