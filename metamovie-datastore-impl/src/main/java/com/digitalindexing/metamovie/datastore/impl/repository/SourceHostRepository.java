package com.digitalindexing.metamovie.datastore.impl.repository;

import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceHostRepository
    extends MongoRepository<SourceHost, String>, SourceHostCustomRepository {
  Optional<SourceHost> findByExternalSourceHostId(String externalSourceHostId);
}
