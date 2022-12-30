package com.movierator.movierator.periodic_jobs.tmdb_fetch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.movierator.movierator.model.Movie;
import com.movierator.movierator.repository.MovieRepository;
import com.movierator.movierator.repository.SeriesRepository;

@Component
public class FetchAllMoviesAndSeries {
  private static final Logger logger = LoggerFactory.getLogger(FetchAllMoviesAndSeries.class);

  @Autowired
  private MovieRepository movieRepo;

  @Autowired
  private SeriesRepository seriesRepo;

  @Autowired
  private TMDBAPIFactory tmdbApiFactory;

  @Scheduled(fixedRate = 60000)
  public void fetchMovies() {
    logger.info("Fetching movies...");
    int totalSavedMovies = 0;

    // TODO: Do pagination in parallel with multiple threads - Would speed up
    // process significantly

    TMDBApi<TMDBMovieResponse> moviesApi = tmdbApiFactory.createForEntity(TMDBEntities.MOVIE);

    List<TMDBMovie> tmdbMovies = moviesApi.getAllEntities();
    List<Movie> movies = new ArrayList<>();
    for (TMDBMovie tmdbMovie : tmdbMovies) {
      movies.add(new Movie(tmdbMovie.getTitle()));
    }

    logger.info("Saved {} movies into database", movies.size());
    movieRepo.saveAll(movies);

    int totalPages = 1;
    for (int page = 1; page <= totalPages && page <= 10; page++) {
      TMDBMovieResponse response = moviesApi.getEntitiesForPage(page);

      totalPages = response.getTotal_pages();

      List<Movie> movies = new ArrayList<>();
      for (TMDBMovie tmdbMovie : response.getMovies()) {
        // TODO: Create mapping iterator instead of saving it into temp variable
        movies.add(new Movie(tmdbMovie.getTitle()));
      }
      // TODO: Update already existing movies instead of only appending
      movieRepo.saveAll(movies);
      totalSavedMovies += movies.size();
      logger.info("Saved {} movies into database", movies.size());
    }

    logger.info("Saved in total {} movies into database", totalSavedMovies);
  }

  private <T extends TMDBEntity> getAllEntitiesFromAPI(TMDBApi api) {
    List<T> entities = new ArrayList<>();
    
    int totalPages = 1;
    for (int page = 1; page <= totalPages && page <= 10; page++) {
      TMDBResponse response = api.getEntitiesForPage(page);

      totalPages = response.getTotal_pages();

      

      for (TMDBMovie tmdbMovie : response.getMovies()) {
        // TODO: Create mapping iterator instead of saving it into temp variable
        movies.add(new Movie(tmdbMovie.getTitle()));
      }
      // TODO: Update already existing movies instead of only appending
      movieRepo.saveAll(movies);
      totalSavedMovies += movies.size();
      logger.info("Saved {} movies into database", movies.size());
    }
  }
}
