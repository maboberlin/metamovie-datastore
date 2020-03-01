package com.digitalindexing.metamovie.datastore.impl.repository.impl;

import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import com.digitalindexing.metamovie.datastore.impl.exception.MoreThanOneEntityFoundException;
import com.digitalindexing.metamovie.datastore.impl.repository.SourceHostCustomRepository;
import com.mongodb.DBRef;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Slf4j
public class SourceHostCustomRepositoryImpl implements SourceHostCustomRepository {

  @Autowired private MongoTemplate mongoTemplate;

  @Autowired private MongoOperations mongoOperations;

  public Optional<SourceHost> findSourceHostBySourceId(String sourceId) {
    Query query =
        new Query(
            Criteria.where(SourceHost.SOURCE_SET_FIELD)
                .is(new DBRef(Source.SOURCES_COLLECTION, sourceId)));

    List<SourceHost> sourceHostList = mongoTemplate.find(query, SourceHost.class);

    if (sourceHostList == null || sourceHostList.isEmpty()) {
      return Optional.empty();
    } else if (sourceHostList.size() > 1) {
      throw new MoreThanOneEntityFoundException(
          SourceHost.class.getTypeName(),
          String.format("More than one source host found for source id '%s'", sourceId));
    } else {
      return Optional.of(sourceHostList.get(0));
    }
  }

  public void removeSource(String sourceId) {
    Query query =
        new Query(
            Criteria.where(SourceHost.SOURCE_SET_FIELD)
                .is(new DBRef(Source.SOURCES_COLLECTION, sourceId)));

    UpdateResult updateResult =
        mongoOperations.upsert(
            query,
            new Update()
                .pull(SourceHost.SOURCE_SET_FIELD, new DBRef(Source.SOURCES_COLLECTION, sourceId)),
            SourceHost.class);

    log.info("{} sources deleted from source host", updateResult.getMatchedCount());
  }
}
