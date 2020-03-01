package com.digitalindexing.metamovie.datastore.impl.controller;

import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.impl.controller.dtos.IDList;
import com.digitalindexing.metamovie.datastore.impl.controller.dtos.Message;
import com.digitalindexing.metamovie.datastore.impl.controller.helper.ResponseMessageService;
import com.digitalindexing.metamovie.datastore.impl.service.MovieService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/movies")
@Validated
public class MovieController {

  @Autowired private ResponseMessageService responseMessageService;

  @Autowired private MovieService movieService;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<Movie> getAllMovies(
      @Min(1) @RequestParam("limit") Integer limit,
      @Min(0) @RequestParam("offset") Integer offset) {
    log.info("Fetching all movies from db");
    return movieService.getAllMovies(limit, offset);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalMovieId}", method = RequestMethod.GET)
  public Movie getMovie(@NotEmpty @PathVariable("externalMovieId") String externalMovieId) {
    log.info("Retrieving movie with id '{}' from db", externalMovieId);
    return movieService.findMovieByExternalId(externalMovieId);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/_idlist", method = RequestMethod.POST)
  public List<Movie> getMoviesByIdList(@NotNull @Valid @RequestBody IDList idList) {
    log.info("Retrieving {} movies from db", idList.size());
    return movieService.findMovieByExternalIdList(idList);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "", method = RequestMethod.POST)
  public Movie createMovie(@NotNull @Valid @RequestBody Movie movie) {
    log.info("Creating movie in db");
    return movieService.saveMovie(movie);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/_bulk", method = RequestMethod.POST)
  public Message createMovieBulk(@NotNull @Valid @RequestBody List<Movie> movieList) {
    log.info("Creating {} movies in db", movieList.size());
    movieService.saveMovieBulk(movieList);
    return responseMessageService.buildResponseMessage("OK");
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/{externalMovieId}/_image", method = RequestMethod.POST)
  public Message uploadImage(
      @NotNull @Valid @RequestParam("file") MultipartFile file,
      @NotEmpty @PathVariable("externalMovieId") String externalMovieId) {
    log.info(
        "Uploading image file '{}' for movie with external id '{}' to db",
        file.getName(),
        externalMovieId);
    movieService.saveDefaultImage(file, externalMovieId);
    return responseMessageService.buildResponseMessage("OK");
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalMovieId}/_image", method = RequestMethod.GET)
  public Resource downloadImage(@NotEmpty @PathVariable("externalMovieId") String externalMovieId) {
    log.info("Downloading image file for movie with external id '{}' from db", externalMovieId);
    return movieService.getDefaultImage(externalMovieId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/{externalMovieId}/_trailer", method = RequestMethod.POST)
  public Message uploadTrailer(
      @NotNull @Valid @RequestParam("file") MultipartFile file,
      @NotEmpty @PathVariable("externalMovieId") String externalMovieId) {
    log.info(
        "Uploading trailer file '{}' for movie with external id '{}' to db",
        file.getName(),
        externalMovieId);
    movieService.saveDefaultTrailer(file, externalMovieId);
    return responseMessageService.buildResponseMessage("OK");
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalMovieId}/_trailer", method = RequestMethod.GET)
  public Resource downloadTrailer(
      @NotEmpty @PathVariable("externalMovieId") String externalMovieId) {
    log.info("Downloading trailer file for movie with external id '{}' from db", externalMovieId);
    return movieService.getDefaultTrailer(externalMovieId);
  }
}
