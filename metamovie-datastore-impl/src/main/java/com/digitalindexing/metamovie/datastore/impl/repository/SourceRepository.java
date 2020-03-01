package com.digitalindexing.metamovie.datastore.impl.repository;

import com.digitalindexing.metamovie.datastore.api.model.Source;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends MongoRepository<Source, String> {
  Optional<Source> findByExternalSourceId(String sourceHostId);
}
