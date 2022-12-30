package com.movierator.movierator.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Series.TABLE)
public class Series extends AbstractMediaEntity {
  private static final long serialVersionUID = -5497910242659399979L;
  public static final String TABLE = "series";

  public Series(String name) {
		this.name = name;
	}

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
