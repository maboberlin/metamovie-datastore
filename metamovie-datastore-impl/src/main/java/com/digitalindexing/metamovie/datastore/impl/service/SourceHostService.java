package com.digitalindexing.metamovie.datastore.impl.service;

import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import java.util.List;

public interface SourceHostService {
  List<SourceHost> getAllSourceHosts();

  SourceHost getSourceHostByExternalId(String externalSourceHostId);

  SourceHost saveSourceHost(SourceHost sourceHost);

  void addSource(SourceHost sourceHost, Source source);

  void removeSource(Source source);
}
