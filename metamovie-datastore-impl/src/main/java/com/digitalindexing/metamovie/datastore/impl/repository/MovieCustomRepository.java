package com.digitalindexing.metamovie.datastore.impl.repository;

import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.api.model.aux.PosterData;
import java.util.Optional;

public interface MovieCustomRepository {
  Optional<Movie> findMovieBySourceId(String sourceId);

  void removeSource(String sourceId);

  boolean updatePosterData(Movie movie, String languageIso639Id, PosterData posterData);
}
