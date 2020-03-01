package com.digitalindexing.metamovie.datastore.impl.service;

import com.digitalindexing.metamovie.datastore.api.model.Source;
import java.util.Set;

public interface SourceService {
  Set<Source> findSourcesByMovieId(String externalMovieId);

  Source addSource(String externalMovieId, String externalSourceHostId, Source source);

  Source deleteSource(String sourceExternalId);
}
