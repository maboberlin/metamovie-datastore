package com.digitalindexing.metamovie.datastore.impl.repository;

import com.digitalindexing.metamovie.datastore.api.model.Movie;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String>, MovieCustomRepository {
  Optional<Movie> findByExternalMovieId(String externalMovieId);

  List<Movie> findByExternalMovieIdIn(Set<String> externalMovieIdList);

  Optional<Movie> findByImdbId(String imdbId);
}
