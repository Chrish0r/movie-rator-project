package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.movierator.movierator.model.MediaEntity;
import com.movierator.movierator.model.MediaEntityType;
import com.movierator.movierator.repository.MediaEntityRepository;

@Component
public class FetchAllMoviesAndSeries {
  private static final Logger logger = LoggerFactory.getLogger(FetchAllMoviesAndSeries.class);

  @Autowired
  private MediaEntityRepository mediaEntityRepo;

  @Autowired
  private TMDBAPIFactory tmdbApiFactory;

  // TODO: Enable Cron loading
  // @Scheduled(cron = "0 0 3 * * *")
  @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
  public void fetchMovies() {
    logger.info("Fetching movies...");
    TMDBApi<TMDBMovieResponse, TMDBMovie> moviesApi = tmdbApiFactory.createForMovies();

    List<TMDBMovie> tmdbMovies = moviesApi.getAllEntities();
    List<MediaEntity> movies = new ArrayList<>();
    for (TMDBMovie tmdbMovie : tmdbMovies) {
      movies.add(new MediaEntity(tmdbMovie.id, MediaEntityType.MOVIE, tmdbMovie.title));
    }

    logger.info("Saved {} movies into database", movies.size());
    mediaEntityRepo.saveAll(movies);
  }

  // @Scheduled(cron = "0 0 3 * * *")
  @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
  public void fetchSeries() {
    logger.info("Fetching series...");
    TMDBApi<TMDBSeriesResponse, TMDBSeries> seriesApi = tmdbApiFactory.createForSeries();

    List<TMDBSeries> tmdbSeries = seriesApi.getAllEntities();
    List<MediaEntity> mediaEntities = new ArrayList<>();
    for (TMDBSeries series : tmdbSeries) {
      mediaEntities.add(new MediaEntity(series.id, MediaEntityType.SERIES, series.name));
    }

    logger.info("Saved {} series into database", mediaEntities.size());
    mediaEntityRepo.saveAll(mediaEntities);
  }
}
