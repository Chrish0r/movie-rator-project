package com.movierator.movierator.model;

public enum MediaEntityType {
	MOVIE("MOVIE"),
	SERIES("SERIES");

	private String type;

	private MediaEntityType(String type) {
		this.type = type;
	}

	public String toString() {
		return this.type;
	}
}
