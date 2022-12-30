package com.movierator.movierator.controller.formObjects;

public class SearchResult {
  private String title;

  public SearchResult(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
