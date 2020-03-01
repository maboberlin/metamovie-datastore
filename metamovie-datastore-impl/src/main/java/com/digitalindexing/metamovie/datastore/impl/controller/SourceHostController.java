package com.digitalindexing.metamovie.datastore.impl.controller;

import com.digitalindexing.metamovie.datastore.api.model.SourceHost;
import com.digitalindexing.metamovie.datastore.impl.controller.helper.ResponseMessageService;
import com.digitalindexing.metamovie.datastore.impl.service.SourceHostService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/source_hosts")
public class SourceHostController {

  @Autowired private ResponseMessageService responseMessageService;

  @Autowired private SourceHostService sourceHostService;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<SourceHost> getAllSourceHosts() {
    log.info("Fetching all source hosts from db");
    return sourceHostService.getAllSourceHosts();
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalSourceHostId}", method = RequestMethod.GET)
  public SourceHost getSourceHost(
      @NotEmpty @PathVariable("externalSourceHostId") String externalSourceHostId) {
    log.info("Retrieving source host in db with id '{}'", externalSourceHostId);
    return sourceHostService.getSourceHostByExternalId(externalSourceHostId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "", method = RequestMethod.POST)
  public SourceHost createSourceHost(@NotNull @Valid @RequestBody SourceHost sourceHost) {
    log.info("Fetching source host in db");
    return sourceHostService.saveSourceHost(sourceHost);
  }
}
