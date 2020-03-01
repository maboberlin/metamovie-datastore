package com.digitalindexing.metamovie.datastore.impl.service.impl;

import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import com.digitalindexing.metamovie.datastore.impl.exception.EntityNotFoundException;
import com.digitalindexing.metamovie.datastore.impl.repository.SourceRepository;
import com.digitalindexing.metamovie.datastore.impl.service.MovieService;
import com.digitalindexing.metamovie.datastore.impl.service.SourceHostService;
import com.digitalindexing.metamovie.datastore.impl.service.SourceService;
import com.digitalindexing.metamovie.datastore.impl.util.IDGenerationService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements SourceService {

  @Autowired private IDGenerationService idGenerationService;

  @Autowired private SourceRepository sourceRepository;

  @Autowired private MovieService movieService;

  @Autowired private SourceHostService sourceHostService;

  @Override
  public Set<Source> findSourcesByMovieId(String externalMovieId) {
    Movie movie = movieService.findMovieByExternalId(externalMovieId);
    return movie.getSourceSet();
  }

  @Override
  public Source addSource(String externalMovieId, String externalSourceHostId, Source source) {
    Movie movie = movieService.findMovieByExternalId(externalMovieId);
    SourceHost sourceHost = sourceHostService.getSourceHostByExternalId(externalSourceHostId);

    source.setId(null);
    source.setExternalSourceId(idGenerationService.generateID());
    source = sourceRepository.save(source);

    movieService.addSource(movie, source);
    sourceHostService.addSource(sourceHost, source);

    return source;
  }

  @Override
  public Source deleteSource(String sourceExternalId) {
    Source source =
        sourceRepository
            .findByExternalSourceId(sourceExternalId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        Source.class.getSimpleName(),
                        String.format("Source not found for external id '%s'", sourceExternalId)));

    movieService.removeSource(source);
    sourceHostService.removeSource(source);

    sourceRepository.delete(source);
    return source;
  }
}
