package com.digitalindexing.metamovie.datastore.impl.service;

import com.digitalindexing.metamovie.datastore.api.messaging.PosterDataMessageElement;
import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.api.model.Source;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {

  List<Movie> getAllMovies(Integer limit, Integer offset);

  Movie findMovieByExternalId(String externalMovieId);

  List<Movie> findMovieByExternalIdList(Set<String> idList);

  Optional<Movie> findMovieByImdbId(String imdbId);

  Movie saveMovie(Movie movie);

  Movie updateMovie(Movie movie);

  void saveMovieBulk(List<Movie> movieList);

  void addSource(Movie movie, Source source);

  void removeSource(Source source);

  void saveDefaultImage(MultipartFile file, String externalMovieId);

  Resource getDefaultImage(String externalMovieId);

  void saveDefaultTrailer(MultipartFile file, String externalMovieId);

  Resource getDefaultTrailer(String externalMovieId);

  boolean saveMoviePosterData(PosterDataMessageElement posterData, Movie movie);
}
