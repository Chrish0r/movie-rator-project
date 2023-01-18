package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movierator.movierator.controller.formObjects.MediaRatingByUserSearchResult;
import com.movierator.movierator.controller.formObjects.Review;
import com.movierator.movierator.controller.formObjects.SearchTerm;
import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.MediaRatingRepository;
import com.movierator.movierator.repository.UserRepository;

@Controller
public class MediaRatingController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MediaRatingRepository mediaRatingRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/search-reviews")
	public String searchReviewsByUser(@RequestParam(name = "searchTerm") String userName, Model model) {
		// interested only in the ten latest reviews
		int limit = 10;
		Pageable pageable = PageRequest.of(0, limit, Sort.by("lastModifiedAt").descending());

		Optional<User> userFoundOpt = userRepository.findUserByLogin(userName);

		if (!userFoundOpt.isPresent() || userFoundOpt.get().getActive() != 1) {
			logger.warn("The user with the user name " + userName + " does not exist!");
			return "user/user-not-found";
		}

//		if (userFoundOpt.get().getMediaRatings() == null || userFoundOpt.get().getMediaRatings().isEmpty()) {
//			logger.warn("The user with the user name " + userName + " does not have any reviews yet");
//			return "reviews-not-found";
//		}

		// limited to ten latest reviews
		List<MediaRating> foundMediaRatings = mediaRatingRepository.getMediaRatingsByUserLimitedTo(userFoundOpt.get(),
				pageable);
//		List<MediaRating> mediaRatings = mediaRatingRepository.getMediaRatingsByUserLimitedTo(userFoundOpt.get(), limit);
		logger.info(
				"The user with the user name " + userName + " has published " + foundMediaRatings.size() + " reviews");
		
		if (foundMediaRatings == null || foundMediaRatings.isEmpty()) {
			logger.warn("The user with the user name " + userName + " does not have any reviews yet");
			return "reviews-not-found";
		}
		
		// list of table items
		List<MediaRatingByUserSearchResult> results = new ArrayList<MediaRatingByUserSearchResult>(
				foundMediaRatings.size());

		for (MediaRating mediaRating : foundMediaRatings) {
			results.add(new MediaRatingByUserSearchResult(mediaRating.getRating(), mediaRating.getReviewText(),
					mediaRating.getLastModifiedAt()));
		}
		model.addAttribute("results", results);
		model.addAttribute("user", userFoundOpt.get());

		return "reviews-search-result";
	}

	@PostMapping("/review/{id}")
	public String validateAndStoreReview(@PathVariable long id, Review review, Model model) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> userOpt = userRepository.findUserByLogin(userName);
		Date dateToInsert = new Date();

		if (!userOpt.isPresent()) {
			logger.warn("The user with the user name " + userName + " does not exist!");
			return "edit-review";
		}

		MediaRating mediaRating = new MediaRating();

		mediaRating.setMediaId(id);
		mediaRating.setRating(review.getRating());
		mediaRating.setReviewText(review.getReviewText());
		mediaRating.setUser(userOpt.get());
		mediaRating.setCreatedAt(dateToInsert);
		mediaRating.setLastModifiedAt(dateToInsert);

		mediaRatingRepository.save(mediaRating);

		return "redirect:/media/" + id;
	}

	@GetMapping("/deleteReview/{id}")
	public String deleteReview(@RequestParam(name = "mediaId") long mediaId, @PathVariable long id) {

		mediaRatingRepository.deleteById(id);

		return "redirect:/media/" + mediaId;
	}

	@GetMapping("/edit/{id}")
	public String editReview(@PathVariable long id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("reviewForm", new Review());
		return "edit-review";
	}

	@PostMapping("/edit/process/{id}")
	public String processEditReview(@PathVariable long id, Review review) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> userOpt = userRepository.findUserByLogin(userName);

		if (!userOpt.isPresent()) {
			logger.warn("The user with the user name " + userName + " does not exist!");
			return "edit-review";
		}

		mediaRatingRepository.updateReviewTextAndRatingByUserNameAndMediaId(review.getReviewText(), review.getRating(),
				new Date(), userOpt.get(), id);

		return "redirect:/media/{id}";
	}
}