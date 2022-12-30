package com.movierator.movierator.periodic_jobs.tmdb_fetch;

public abstract class TMDBResponse {
  private int page;
  private int total_pages;

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
}
