package com.movierator.movierator.periodic_jobs.tmdb_fetch;

public class TMDBResponse<T> {
  private int page;
  private int total_pages;
  private T[] results;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getTotal_pages() {
    return total_pages;
  }

  public void setTotal_pages(int total_pages) {
    this.total_pages = total_pages;
  }

  public T[] getResults() {
    return results;
  }

  public void setResults(T[] results) {
    this.results = results;
  }
}
