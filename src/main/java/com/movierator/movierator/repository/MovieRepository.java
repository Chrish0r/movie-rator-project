package com.movierator.movierator.repository;

import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {  }
