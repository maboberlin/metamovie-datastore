package com.digitalindexing.metamovie.datastore.api.client;

import com.digitalindexing.metamovie.datastore.api.config.MetaMovieClientConfiguration;
import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(
  value = "sourcehostclient",
  url = "${metamovie.datastore.client.url}",
  configuration = MetaMovieClientConfiguration.class
)
public interface SourceHostControllerClient {

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  List<SourceHost> getAllSourceHosts();

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalSourceHostId}", method = RequestMethod.GET)
  SourceHost getSourceHost(
      @NotEmpty @PathVariable("externalSourceHostId") String externalSourceHostId);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "", method = RequestMethod.POST)
  SourceHost createSourceHost(@NotNull @Valid @RequestBody SourceHost sourceHost);
}
