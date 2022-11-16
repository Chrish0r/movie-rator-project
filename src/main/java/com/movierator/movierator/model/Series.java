package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Series.TABLE)
public class Series extends MediaEntity {
	public static final String TABLE = "series";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	public Series() {
		
	}

}
