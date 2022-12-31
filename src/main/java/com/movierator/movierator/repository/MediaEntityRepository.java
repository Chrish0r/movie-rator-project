package com.movierator.movierator.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.Media;

public interface MediaEntityRepository extends CrudRepository<Media, Long> {
  public List<Media> findByNameContaining(String nameFragment);
}
