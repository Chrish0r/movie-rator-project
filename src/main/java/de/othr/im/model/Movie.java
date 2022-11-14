package de.othr.im.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Movie.TABLE)
public class Movie extends MediaEntity {
	
public static final String TABLE = "movies";
	
	public Movie() {
		
	}

}
