package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

class TMDBApi {
  private static final Logger logger = LoggerFactory.getLogger(TMDBApi.class);

  private HttpEntity<String> httpEntity;
  private String urlTemplate;

  private RestTemplate restTemplate;

  private TMDBConfig config;

  TMDBApi(RestTemplate restTemplate, TMDBConfig tmdbConfig, String urlEndpoint) {
    this.restTemplate = restTemplate;
    this.config = tmdbConfig;

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

  public List<TMDBMovie> getAllEntities() {
    List<TMDBMovie> movies = new ArrayList<>();

    int totalPages = 1;
    for (int page = 1; page <= totalPages && page <= 10; page++) {
      TMDBResponse response = this.getEntitiesForPage(page);

      totalPages = response.getTotal_pages();

      movies.addAll(response.getResults());
    }
  }

  private TMDBResponse getEntitiesForPage(int page) {
    // TODO: RestTemplate is synchron. Blockiert das die Anwendung oder läuft das in
    // einem separaten Thread?
    // TODO: Think about using WebClient. Its more modern and RestTemplate is only
    // in maintainance mode

    Map<String, String> params = new HashMap<>();
    params.put("page", Integer.toString(page));

    ResponseEntity<TMDBResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity,
        TMDBResponse.class, params);
    logger.info("Got response with status {} for page {}", response.getStatusCode().value(), page);

    return response.getBody();
  }
}
