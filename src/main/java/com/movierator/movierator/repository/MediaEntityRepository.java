package com.movierator.movierator.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.MediaEntity;

public interface MediaEntityRepository extends CrudRepository<MediaEntity, Long> {
  public List<MediaEntity> findByNameContaining(String nameFragment);
}
