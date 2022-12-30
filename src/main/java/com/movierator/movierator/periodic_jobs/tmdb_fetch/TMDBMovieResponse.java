package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import com.fasterxml.jackson.annotation.JsonSetter;

public class TMDBMovieResponse {
  private int page;
  private TMDBMovie[] movies;
  private int total_pages;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public TMDBMovie[] getMovies() {
    return movies;
  }

  @JsonSetter("results")
  public void setMovies(TMDBMovie[] movies) {
    this.movies = movies;
  }

  public int getTotal_pages() {
    return total_pages;
  }

  public void setTotal_pages(int total_pages) {
    this.total_pages = total_pages;
  }
}
