package com.movierator.movierator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.movierator.movierator.model.MediaRating;

public interface MediaRatingRepository extends CrudRepository<MediaRating, Long> {
  List<MediaRating> getByMediaId(Long mediaId); 

  @Query("SELECT AVG(r.rating) FROM MediaRating r WHERE r.mediaId = ?1")
  Float getAverageRatingByMediaId(Long mediaId);

	@Query("SELECT r FROM MediaRating r WHERE r.userName = ?1 AND r.mediaId = ?2")
	MediaRating getByUserNameAndMediaId(String userName, long mediaId);

	@Transactional
	@Modifying
	@Query("UPDATE MediaRating r SET r.reviewText = :reviewText, r.rating = :rating WHERE r.userName = :userName AND r.mediaId = :mediaId")
	void updateReviewTextAndRatingByUserNameAndMediaId(@Param("reviewText") String reviewText,
			@Param("rating") int rating, @Param("userName") String userName, @Param("mediaId") long mediaId);
}
