package com.movierator.movierator.tmdbApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TMDBApiFactory {
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private TMDBConfig config;

  public TMDBApi<TMDBMovieResponse, TMDBMovie> createForMovies() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBApi<TMDBMovieResponse, TMDBMovie>(restTemplate, config, "/movie", TMDBMovieResponse.class, TMDBMovie.class);
  }

  public TMDBApi<TMDBSeriesResponse, TMDBSeries> createForSeries() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBApi<TMDBSeriesResponse, TMDBSeries>(restTemplate, config, "/tv", TMDBSeriesResponse.class, TMDBSeries.class);
  }

  public TMDBApi<TMDBActorResponse, TMDBActor> createForActors() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBApi<TMDBActorResponse, TMDBActor>(restTemplate, config, "/person", TMDBActorResponse.class, TMDBActor.class);
  }
}
