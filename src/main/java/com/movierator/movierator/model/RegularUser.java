package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = RegularUser.TABLE)
public class RegularUser extends AbstractRole {

	private static final long serialVersionUID = 5088115185535822969L;
	public static final String TABLE = "regular_users";
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
