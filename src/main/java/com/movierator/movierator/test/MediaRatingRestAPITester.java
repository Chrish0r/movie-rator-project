package com.movierator.movierator.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.User;

@SuppressWarnings("deprecation")
@Component
public class MediaRatingRestAPITester {

	// represents client i.e., represents client request
	private final RestTemplate restTemplate;

	@Autowired
	public MediaRatingRestAPITester(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	/**
	 * 
	 * @param id user id of the user being searched for, i.e., found reviews are
	 *           assigned to the underlying user
	 * @return list of the ten most recent ratings from the relevant user
	 */
	@SuppressWarnings("unused")
	public List<MediaRating> testGetMediaRatingsByUserIdRestAPI(@PathVariable Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<MediaRating[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/reviews/" + id,
				MediaRating[].class);
		MediaRating[] mediaRatings = responseEntity.getBody();

		ObjectMapper mapper = new ObjectMapper();

		mapper.registerModule(new JSR310Module());

		return (List<MediaRating>) Arrays.stream(mediaRatings)
				.map(object -> mapper.convertValue(object, MediaRating.class)).collect(Collectors.toList());
	}
}
