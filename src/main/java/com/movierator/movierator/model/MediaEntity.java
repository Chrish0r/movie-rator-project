package com.movierator.movierator.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = MediaEntity.TABLE)
public class MediaEntity implements Serializable {
	static final String TABLE = "media";
	private static final long serialVersionUID = -4761614502676589955L;
	
	@Id
	private long id;

	@Column(columnDefinition = "VARCHAR(30)")
	@Enumerated(EnumType.STRING)
	private MediaEntityType type;
	
	private String name;

	public MediaEntity() {}

	public MediaEntity(long id, MediaEntityType type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MediaEntityType getType() {
		return this.type;
	}

	public void setType(MediaEntityType type) {
		this.type = type;
	}
}
