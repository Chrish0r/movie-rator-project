package com.movierator.movierator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.MediaRating;

public interface MediaRatingRepository extends CrudRepository<MediaRating, Long> {
  List<MediaRating> getByMediaId(Long mediaId); 

  @Query("SELECT AVG(r.rating) FROM MediaRating r WHERE r.mediaId = ?1")
  Float getAverageRatingByMediaId(Long mediaId);
  
  @Query("SELECT r from MediaRating r Where r.mediaId = ?1")
  List<MediaRating> getAllRatingsForMediaId(Long mediaId);
}
