package com.movierator.movierator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.User;

public interface MediaRatingRepository extends CrudRepository<MediaRating, Long> {
  List<MediaRating> getByMediaId(Long mediaId); 

  @Query("SELECT AVG(r.rating) FROM MediaRating r WHERE r.mediaId = ?1")
  Float getAverageRatingByMediaId(Long mediaId);

	@Query("SELECT r FROM MediaRating r WHERE r.user = ?1 AND r.mediaId = ?2")
	MediaRating getByUserNameAndMediaId(User user, long mediaId);

	@Transactional
	@Modifying
	@Query("UPDATE MediaRating r SET r.reviewText = :reviewText, r.rating = :rating WHERE r.user = :user AND r.mediaId = :mediaId")
	void updateReviewTextAndRatingByUserNameAndMediaId(@Param("reviewText") String reviewText,
			@Param("rating") int rating, @Param("user") User user, @Param("mediaId") long mediaId);
	
}
