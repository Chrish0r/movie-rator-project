package com.movierator.movierator.repository;

import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.Series;

public interface SeriesRepository extends CrudRepository<Series, Long> {  }
