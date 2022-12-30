package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class TMDBAPIFactory {
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private TMDBConfig config;

  public TMDBApi createForEntity(TMDBEntities entity) {
    switch (entity) {
      case MOVIE:
        return new TMDBApi<TMDBMovieResponse>(restTemplateBuilder.build(), config, "/movie/popular", TMDBMovieResponse.class);
      case SERIES:
        return new TMDBApi<TMDBSeriesResponse>(restTemplateBuilder.build(), config, "/tv/popular", TMDBSeriesResponse.class);
      default:
        // Why is this necessary? This should never happen!
        throw new RuntimeException("Invalid entity type");
    }
  }
}
