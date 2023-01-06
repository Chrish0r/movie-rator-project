package com.movierator.movierator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.MediaRating;

public interface MediaRatingRepository extends CrudRepository<MediaRating, Long> {
  List<MediaRating> getByMediaId(Long mediaId); 

  @Query("SELECT AVG(r.rating) FROM MediaRating r WHERE r.mediaId = ?1")
  Float getAverageRatingByMediaId(Long mediaId);
}
