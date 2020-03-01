package com.digitalindexing.metamovie.datastore.api.client;

import com.digitalindexing.metamovie.datastore.api.config.MetaMovieClientConfiguration;
import com.digitalindexing.metamovie.datastore.api.model.Source;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(
  value = "sourceclient",
  url = "${metamovie.datastore.client.url}",
  configuration = MetaMovieClientConfiguration.class
)
public interface SourceControllerClient {

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  Set<Source> findSourcesByMovieId(@NotEmpty @RequestParam(value = "movie_id") String movieId);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "", method = RequestMethod.POST)
  Source addSourceToMovie(
      @NotEmpty @RequestParam(value = "movie_id") String externalMovieId,
      @NotEmpty @RequestParam(value = "source_host_id") String externalSourceHostId,
      @NotNull @Valid @RequestBody Source source);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{sourceExternalId}", method = RequestMethod.DELETE)
  Source addSourceToMovie(@NotEmpty @PathVariable("sourceExternalId") String sourceExternalId);
}
