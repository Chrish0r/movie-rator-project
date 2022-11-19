package com.movierator.movierator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This class is representing the entitiy {@link User} with its various attributes.
 * 
 * @author Przemyslaw Christof Gadek
 *
 */

//@Entity
//@Table(name = User.TABLE)
public class User implements UserDetails {
	
	private static final long serialVersionUID = 6994341383205631485L;

	public static final String TABLE = "users";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "User name is mandatory")
	@Column(name = "user name" , unique = true)
	private String login;

	@NotEmpty(message = "Email is mandatory")
	@Column(unique = true)
	private String email;
	
	@NotEmpty(message = "Password is mandatory")
	@Size(min = 4, message = "Password must contain at least 4 characters")
	private String password;
	
//	private Integer active;
//	private Integer usertype;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//			name="userauthority",
//			joinColumns = @JoinColumn(name="iduser"),
//			inverseJoinColumns = @JoinColumn(name="idauthority")
//			)
//	private List<Authority> myauthorities = new ArrayList<Authority>();
	
	public User() {
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getUsername() {
		return this.login;
	}
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
//	public Integer getActive() {
//		return active;
//	}
//
//	public void setActive(Integer active) {
//		this.active = active;
//	}
//
//	public Integer getUsertype() {
//		return usertype;
//	}
//
//	public void setUsertype(Integer usertype) {
//		this.usertype = usertype;
//	}
//
//	public String getLogin() {
//		return login;
//	}
//
//	public void setLogin(String login) {
//		this.login = login;
//	}
//
//	public List<Authority> getMyauthorities() {
//		return myauthorities;
//	}
//
//	public void setMyauthorities(List<Authority> myauthorities) {
//		this.myauthorities = myauthorities;
//	}

}
