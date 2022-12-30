package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import com.fasterxml.jackson.annotation.JsonSetter;

public class TMDBMovieResponse extends TMDBResponse {
  private TMDBMovie[] movies;

  public TMDBMovie[] getMovies() {
    return movies;
  }

  @JsonSetter("results")
  public void setMovies(TMDBMovie[] movies) {
    this.movies = movies;
  }
}
