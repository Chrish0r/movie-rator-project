package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Series.TABLE)
public class Series extends MediaEntity {

	private static final long serialVersionUID = -5497910242659399979L;
	public static final String TABLE = "series";
	
	public Series() {
		
	}

}
