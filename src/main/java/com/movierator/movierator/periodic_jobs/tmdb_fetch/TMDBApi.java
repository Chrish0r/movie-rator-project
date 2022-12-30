package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import java.util.HashMap;
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

// TODO: Make this generic to be usable for series too<

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

class TMDBApi<T extends TMDBResponse> {
  private static final Logger logger = LoggerFactory.getLogger(TMDBApi.class);

  private HttpEntity<String> httpEntity;
  private String urlTemplate;

  private RestTemplate restTemplate;

  private TMDBConfig config;

  private Class<T> responseClass;

  TMDBApi(RestTemplate restTemplate, TMDBConfig tmdbConfig, String urlEndpoint, Class<T> response) {
    this.restTemplate = restTemplate;
    this.config = tmdbConfig;
    this.responseClass = response;

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + config.getApiToken());
    httpEntity = new HttpEntity<>(headers);

    String url = config.getUrl() + urlEndpoint;
    urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("language", "de")
        .queryParam("page", "{page}")
        .encode()
        .toUriString();
  }

  public T getEntitiesForPage(int page) {
    // TODO: RestTemplate is synchron. Blockiert das die Anwendung oder läuft das in
    // einem separaten Thread?
    // TODO: Think about using WebClient. Its more modern and RestTemplate is only
    // in maintainance mode

    Map<String, String> params = new HashMap<>();
    params.put("page", Integer.toString(page));

    ResponseEntity<T> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity,
        this.responseClass, params);
    logger.info("Got response with status {} for page {}", response.getStatusCode().value(), page);

    return response.getBody();
  }
}

enum TMDBEntities {
  MOVIE,
  SERIES
}
