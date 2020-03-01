package com.digitalindexing.metamovie.datastore.impl.service.impl;

import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import com.digitalindexing.metamovie.datastore.impl.exception.EntityNotFoundException;
import com.digitalindexing.metamovie.datastore.impl.repository.SourceHostRepository;
import com.digitalindexing.metamovie.datastore.impl.repository.impl.SourceHostCustomRepositoryImpl;
import com.digitalindexing.metamovie.datastore.impl.service.SourceHostService;
import com.digitalindexing.metamovie.datastore.impl.util.IDGenerationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceHostServiceImpl implements SourceHostService {

  @Autowired private SourceHostRepository sourceHostRepository;

  @Autowired private SourceHostCustomRepositoryImpl sourceHostCustomRepositoryImpl;

  @Autowired private IDGenerationService idGenerationService;

  @Override
  public List<SourceHost> getAllSourceHosts() {
    return sourceHostRepository.findAll();
  }

  @Override
  public SourceHost getSourceHostByExternalId(String externalSourceHostId) {
    return sourceHostRepository
        .findByExternalSourceHostId(externalSourceHostId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    SourceHost.class.getSimpleName(),
                    String.format(
                        "Source host not found for external id '%s'", externalSourceHostId)));
  }

  @Override
  public SourceHost saveSourceHost(SourceHost sourceHost) {
    sourceHost.setId(null);
    sourceHost.setExternalSourceHostId(idGenerationService.generateID());
    return sourceHostRepository.save(sourceHost);
  }

  @Override
  public void addSource(SourceHost sourceHost, Source source) {
    sourceHost.getSourceSet().add(source);
    sourceHostRepository.save(sourceHost);
  }

  @Override
  public void removeSource(Source source) {
    sourceHostCustomRepositoryImpl.removeSource(source.getId());
  }
}
