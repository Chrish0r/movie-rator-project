//package com.movierator.movierator.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = Admin.TABLE)
//public class Admin {
//
//	public static final String TABLE = "admins";
//	
//	@Id
//	@GeneratedValue (strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Column(name = "_first name")
//	private String firstName;
//	@Column(name = "_last name")
//	private String lastName;
//	private String email;
//	
//	@OneToOne
//	@JoinColumn(name = "user_id", referencedColumnName = "id")
//	User user;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//}
