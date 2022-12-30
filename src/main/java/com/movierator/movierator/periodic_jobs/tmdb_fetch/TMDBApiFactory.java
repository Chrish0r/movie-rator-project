package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class TMDBAPIFactory {
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private TMDBConfig config;

  public TMDBApi<TMDBMovieResponse, TMDBMovie> createForMovies() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBApi<TMDBMovieResponse, TMDBMovie>(restTemplate, config, "/movie/popular", TMDBMovieResponse.class);
  }

  public TMDBApi<TMDBSeriesResponse, TMDBSeries> createForSeries() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBApi<TMDBSeriesResponse, TMDBSeries>(restTemplate, config, "/tv/popular", TMDBSeriesResponse.class);
  }
}
