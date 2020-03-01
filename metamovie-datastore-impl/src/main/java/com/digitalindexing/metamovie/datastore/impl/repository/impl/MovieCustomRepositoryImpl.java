package com.digitalindexing.metamovie.datastore.impl.repository.impl;

import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.api.model.aux.PosterData;
import com.digitalindexing.metamovie.datastore.api.model.aux.TranslationData;
import com.digitalindexing.metamovie.datastore.impl.exception.MoreThanOneEntityFoundException;
import com.digitalindexing.metamovie.datastore.impl.repository.MovieCustomRepository;
import com.mongodb.DBRef;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MovieCustomRepositoryImpl implements MovieCustomRepository {

  @Autowired private MongoTemplate mongoTemplate;

  @Autowired private MongoOperations mongoOperations;

  public Optional<Movie> findMovieBySourceId(String sourceId) {
    Query query =
        new Query(
            Criteria.where(Movie.SOURCE_SET_FIELD)
                .is(new DBRef(Source.SOURCES_COLLECTION, sourceId)));

    List<Movie> movieList = mongoTemplate.find(query, Movie.class);

    if (movieList == null || movieList.isEmpty()) {
      return Optional.empty();
    } else if (movieList.size() > 1) {
      throw new MoreThanOneEntityFoundException(
          Movie.class.getTypeName(),
          String.format("More than one movie found for source id '%s'", sourceId));
    } else {
      return Optional.of(movieList.get(0));
    }
  }

  public void removeSource(String sourceId) {
    Query query =
        new Query(
            Criteria.where(Movie.SOURCE_SET_FIELD)
                .is(new DBRef(Source.SOURCES_COLLECTION, sourceId)));

    UpdateResult updateResult =
        mongoOperations.upsert(
            query,
            new Update()
                .pull(Movie.SOURCE_SET_FIELD, new DBRef(Source.SOURCES_COLLECTION, sourceId)),
            Movie.class);

    log.debug("{} sources deleted from movie", updateResult.getMatchedCount());
  }

  public boolean updatePosterData(Movie movie, String languageIso639Id, PosterData posterData) {
    int translationDataIx = getTranslationIndexForLanguage(movie, languageIso639Id);
    Query query =
        Query.query(
            Criteria.where(Movie.EXTERNAL_ID_FIELD)
                .is(movie.getExternalMovieId())
                .and(
                    String.format(
                        "%s.%s",
                        Movie.TRANSLATION_DATA_FIELD, TranslationData.TRANSLATION_ISO_639_ID_FIELD))
                .is(languageIso639Id));
    query
        .fields()
        .include(Movie.TRANSLATION_DATA_FIELD)
        .slice(Movie.TRANSLATION_DATA_FIELD, translationDataIx, 1);

    UpdateResult updateResult =
        mongoTemplate.updateMulti(
            query,
            new Update()
                .set(
                    String.format(
                        "%s.$.%s", Movie.TRANSLATION_DATA_FIELD, TranslationData.POSTER_DATA_FIELD),
                    posterData),
            Movie.class);

    boolean updated = updateResult.getModifiedCount() > 0;
    log.debug(
        "Updated poster data for movie with external ID '{}': {}",
        movie.getExternalMovieId(),
        updated);
    return updated;
  }

  private int getTranslationIndexForLanguage(Movie movie, String languageIso639Id) {
    for (int i = 0; i < movie.getTranslationDataList().size(); i++) {
      if (movie.getTranslationDataList().get(i).getIso639Id().equalsIgnoreCase(languageIso639Id)) {
        return i;
      }
    }
    throw new IllegalArgumentException(
        String.format("No translation found for language '%s'", languageIso639Id));
  }
}
