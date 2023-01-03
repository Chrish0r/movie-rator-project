package com.movierator.movierator.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = MediaRating.TABLE, indexes = @Index(name = "mediaId", columnList = "mediaId"))
public class MediaRating implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final String TABLE = "media_rating";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "mediaId is required")
  private long mediaId;

  @NotEmpty(message = "rating is required")
  @DecimalMax(value = "5", message = "Rating must be less or equal 6")
  @DecimalMin(value = "0", message = "Rating must be more or equal 0")
  private int rating;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getMediaId() {
    return mediaId;
  }

  public void setMediaId(long mediaId) {
    this.mediaId = mediaId;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}
