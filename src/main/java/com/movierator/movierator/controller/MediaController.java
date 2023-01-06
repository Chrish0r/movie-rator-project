package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movierator.movierator.controller.formObjects.MediaDetail;
import com.movierator.movierator.controller.formObjects.MediaSearchResult;
import com.movierator.movierator.controller.formObjects.Review;
import com.movierator.movierator.controller.formObjects.SearchTerm;
import com.movierator.movierator.model.Media;
import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.repository.MediaRatingRepository;
import com.movierator.movierator.repository.MediaRepository;


@Controller
public class MediaController {
  private MediaRepository mediaEntityRepository;
  private MediaRatingRepository mediaRatingRepository;
  
  public MediaController(MediaRepository mediaEntityRepository, MediaRatingRepository mediaRatingRepository) {
    this.mediaEntityRepository = mediaEntityRepository;
    this.mediaRatingRepository = mediaRatingRepository;
  }
  
  @GetMapping("/search-media")
  public String searchMedia(@RequestParam(name = "searchTerm") String searchTermRaw, Model model) {
    List<Media> foundMedia = this.mediaEntityRepository.findByNameContaining(searchTermRaw);
    List<MediaSearchResult> results = new ArrayList<>(foundMedia.size());
    
    for (Media media : foundMedia) {
      results.add(new MediaSearchResult(media.getId(), media.getName()));
    }
    model.addAttribute("results", results);

    // To show the search term on the result page it has to be passed as attribute
    SearchTerm searchTerm = new SearchTerm(searchTermRaw);
    model.addAttribute("searchTerm", searchTerm);

    return "media-search-result";
  }

	@GetMapping(value = "/media/{id}")
	public String showMedia(@PathVariable long id, Model model) {
		Optional<Media> media = this.mediaEntityRepository.findById(id);
		List<MediaRating> allReviewsForMedia = this.mediaRatingRepository.getByMediaId(id);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		boolean hasUserAlreadyReviewed = false;
		boolean ratingsEmpty = true;
		
		if (!allReviewsForMedia.isEmpty()) {
			ratingsEmpty = false;
			for(MediaRating rating : allReviewsForMedia) {
//				reviews.add(new Review());
				if(!hasUserAlreadyReviewed) {
					if (rating.getUserName().equals(userName)) {
						hasUserAlreadyReviewed = true;
					}
				}
			}
		}
		System.out.println("reviewed: " + hasUserAlreadyReviewed);
		model.addAttribute("reviewResults", allReviewsForMedia);
		model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);
		model.addAttribute("reviewForm", new Review());
		model.addAttribute("ratingsEmpty", ratingsEmpty);
		
		if (media.isEmpty()) {
			return "media-not-found";
		}

		Float avgRating = mediaRatingRepository.getAverageRatingByMediaId(id);
		MediaDetail mediaDetail = new MediaDetail(media.get().getId(), media.get().getType(), media.get().getName(),
				avgRating);
		model.addAttribute("media", mediaDetail);
		return "media-detail";
	}
	
	@PostMapping("/review/{id}")
	public String validateAndStoreReview(@PathVariable long id, Review review) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		MediaRating mediaRating = new MediaRating();
		
		mediaRating.setMediaId(id);
		mediaRating.setRating(review.getRating());
		mediaRating.setReviewText(review.getReviewText());
		mediaRating.setUserName(userName);
		
		mediaRatingRepository.save(mediaRating);
		
		return "media-detail";
	}
}
