package com.movierator.movierator.periodic_jobs.tmdb_fetch;

public abstract class TMDBResponse<T> {
  public int page;
  public int total_pages;
  public T[] results;
}
