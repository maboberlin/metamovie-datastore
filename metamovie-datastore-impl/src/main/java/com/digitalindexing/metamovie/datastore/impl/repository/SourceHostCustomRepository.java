package com.digitalindexing.metamovie.datastore.impl.repository;

import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import java.util.Optional;

public interface SourceHostCustomRepository {
  Optional<SourceHost> findSourceHostBySourceId(String sourceId);

  void removeSource(String sourceId);
}
