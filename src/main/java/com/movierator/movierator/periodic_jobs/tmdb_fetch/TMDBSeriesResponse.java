package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import com.fasterxml.jackson.annotation.JsonSetter;

public class TMDBSeriesResponse extends TMDBResponse {
  private TMDBSeries[] series;

  public TMDBSeries[] getMovies() {
    return series;
  }

  @JsonSetter("results")
  public void setSeries(TMDBSeries[] series) {
    this.series = series;
  }
}
