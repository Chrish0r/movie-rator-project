package com.movierator.movierator.controller.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.movierator.movierator.model.User;
import com.movierator.movierator.model.dto.Holiday;

@Controller
@RequestMapping("/api/holidays")
public class PublicHolidaysRestController {

	private final RestTemplate restTemplate;

	@Autowired
	public PublicHolidaysRestController(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@GetMapping("/{searchTerm}")
	public String consumePublicHolidaysByYearAndCountry(@PathVariable String searchTerm, Model model) {

		ResponseEntity<Holiday[]> responseEntity = restTemplate
				.getForEntity("https://date.nager.at/api/v2/publicholidays/" + searchTerm, Holiday[].class);

		// extracting body into array of {@link Holiday}s
		Holiday[] holidays = responseEntity.getBody();

		ObjectMapper mapper = new ObjectMapper();

		mapper.registerModule(new JSR310Module());

		model.addAttribute("holidays", holidays);

		return "/holiday/holiday-list";

	}
	
//	@GetMapping("/{year}/{countryCode}")
//	public String consumePublicHolidaysByYearAndCountry(@PathVariable String year, @PathVariable String countryCode, Model model) {
//
//		ResponseEntity<Holiday[]> responseEntity = restTemplate
//				.getForEntity("https://date.nager.at/api/v2/publicholidays/" + year +"/" + countryCode, Holiday[].class);
//
//		// extracting body into array of {@link Holiday}s
//		Holiday[] holidays = responseEntity.getBody();
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		mapper.registerModule(new JSR310Module());
//
//		model.addAttribute("holidays", holidays);
//
//		return "/holiday/holiday-list";
//
//	}
}
