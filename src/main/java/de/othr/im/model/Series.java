package de.othr.im.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Series.TABLE)
public class Series extends MediaEntity {
	

	public static final String TABLE = "series";
	
	
	public Series() {
		
	}

}
