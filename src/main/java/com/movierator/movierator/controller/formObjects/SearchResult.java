package com.movierator.movierator.controller.formObjects;

public class SearchResult {
  private String title;
  private long id;

  public SearchResult(long id, String title) {
    this.id = id;
    this.title = title;
  }

  public long getId() {
    return id;
  }

  // public void setId(long id) {
  //   this.id = id;
  // }

  public String getTitle() {
    return title;
  }

  // public void setTitle(String title) {
  //   this.title = title;
  // }
}
