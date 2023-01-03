package com.movierator.movierator.model;

import java.io.Serializable;

public class Media implements Serializable {

	private static final long serialVersionUID = -7206896593449228130L;
	
	private long id;
	private MediaType type;
	private String name;

	public Media(long id, MediaType type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public MediaType getType() {
		return this.type;
	}
}
