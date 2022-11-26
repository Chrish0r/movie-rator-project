package com.movierator.movierator.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMediaEntity implements Serializable {

	private static final long serialVersionUID = -4761614502676589955L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long id;

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
