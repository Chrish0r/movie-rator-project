package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Movie.TABLE)
public class Movie extends MediaEntity {
	
	private static final long serialVersionUID = 8788023230378376597L;
	public static final String TABLE = "movies";

	public Movie() {
		
	}

}
