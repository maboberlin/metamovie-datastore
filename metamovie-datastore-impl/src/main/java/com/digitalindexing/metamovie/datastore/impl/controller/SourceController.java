package com.digitalindexing.metamovie.datastore.impl.controller;

import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.impl.controller.helper.ResponseMessageService;
import com.digitalindexing.metamovie.datastore.impl.service.SourceService;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/sources")
public class SourceController {

  @Autowired private ResponseMessageService responseMessageService;

  @Autowired private SourceService sourceService;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  public Set<Source> findSourcesByMovieId(
      @NotEmpty @RequestParam(value = "movie_id") String movieId) {
    log.info("Retrieving all sources for movie id '{}'", movieId);
    return sourceService.findSourcesByMovieId(movieId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "", method = RequestMethod.POST)
  public Source addSourceToMovie(
      @NotEmpty @RequestParam(value = "movie_id") String externalMovieId,
      @NotEmpty @RequestParam(value = "source_host_id") String externalSourceHostId,
      @NotNull @Valid @RequestBody Source source) {
    log.info("Adding source to movie with id '{}'", externalMovieId);
    return sourceService.addSource(externalMovieId, externalSourceHostId, source);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{sourceExternalId}", method = RequestMethod.DELETE)
  public Source addSourceToMovie(
      @NotEmpty @PathVariable("sourceExternalId") String sourceExternalId) {
    log.info("Deleting source with id '{}'", sourceExternalId);
    return sourceService.deleteSource(sourceExternalId);
  }
}
