package com.movierator.movierator.repository;

import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.MediaEntity;

public interface MediaEntityRepository extends CrudRepository<MediaEntity, Long> {  }
