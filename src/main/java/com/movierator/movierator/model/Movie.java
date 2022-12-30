package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Movie.TABLE)
public class Movie extends AbstractMediaEntity {
	private static final long serialVersionUID = 8788023230378376597L;
	public static final String TABLE = "movies";

	public Movie(long id, String name) {
		this.name = name;
		
		this.setId(id);
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
