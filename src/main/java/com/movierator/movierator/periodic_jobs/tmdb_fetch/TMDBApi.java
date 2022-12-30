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

class TMDBApi<T extends TMDBResponse<U>, U> {
  private static final Logger logger = LoggerFactory.getLogger(TMDBApi.class);

  private HttpEntity<String> httpEntity;
  private String urlTemplate;

  private RestTemplate restTemplate;

  private TMDBConfig config;
  private Class<T> responseClass;

  TMDBApi(RestTemplate restTemplate, TMDBConfig tmdbConfig, String urlEndpoint, Class<T> responseClass) {
    this.restTemplate = restTemplate;
    this.config = tmdbConfig;
    this.responseClass = responseClass;

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

  public List<U> getAllEntities() {
    List<U> entities = new ArrayList<>();

    // TODO: Do pagination in parallel with multiple threads - Would speed up
    // process significantly

    int totalPages = 1;
    for (int page = 1; page <= totalPages; page++) {
      try {
        T response = this.getEntitiesForPage(page);

        totalPages = response.total_pages;

        for (U resultEntity : response.results) {
          entities.add(resultEntity);
        }
      } catch (Exception e) {
        logger.error("Failed to get entities for page {} with error: {} Skipping page...", page, e.toString());
      }
    }

    return entities;
  }

  private T getEntitiesForPage(int page) {
    Map<String, String> params = new HashMap<>();
    params.put("page", Integer.toString(page));

    ResponseEntity<T> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity, this.responseClass,
        params);
    logger.info("Got response with status {} for page {}", response.getStatusCode().value(), page);

    return response.getBody();
  }
}
