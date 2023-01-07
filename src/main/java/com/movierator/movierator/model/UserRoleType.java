package com.movierator.movierator.model;

public enum UserRoleType {
	ADMIN("ADMIN"), MODERATOR("MODERATOR"), REGULAR_USER("REGULAR_USER");

	private String description;

	private UserRoleType(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.description;
	}
}
