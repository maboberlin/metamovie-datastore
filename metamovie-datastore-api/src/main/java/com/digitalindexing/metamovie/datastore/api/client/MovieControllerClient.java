package com.digitalindexing.metamovie.datastore.api.client;

import com.digitalindexing.metamovie.datastore.api.config.MetaMovieClientConfiguration;
import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.api.model.aux.IDList;
import com.digitalindexing.metamovie.datastore.api.model.aux.Message;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
  value = "movieclient",
  url = "${metamovie.datastore.client.url}",
  path = "/movies",
  configuration = MetaMovieClientConfiguration.class
)
public interface MovieControllerClient {

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "", method = RequestMethod.GET)
  List<Movie> getAllMovies(
      @Min(1) @RequestParam("limit") Integer limit, @Min(0) @RequestParam("offset") Integer offset);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalMovieId}", method = RequestMethod.GET)
  Movie getMovie(@NotEmpty @PathVariable("externalMovieId") String externalMovieId);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/_idlist", method = RequestMethod.POST)
  List<Movie> getMoviesByIdList(@NotNull @Valid @RequestBody IDList idList);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "", method = RequestMethod.POST)
  Movie createMovie(@NotNull @Valid @RequestBody Movie movie);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/_bulk", method = RequestMethod.POST)
  Message createMovieBulk(@NotNull @Valid @RequestBody List<Movie> movieList);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/{externalMovieId}/_image", method = RequestMethod.POST)
  Message uploadImage(
      @NotNull @Valid @RequestParam("file") MultipartFile file,
      @NotEmpty @PathVariable("externalMovieId") String externalMovieId);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalMovieId}/_image", method = RequestMethod.GET)
  Resource downloadImage(@NotEmpty @PathVariable("externalMovieId") String externalMovieId);

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/{externalMovieId}/_trailer", method = RequestMethod.POST)
  Message uploadTrailer(
      @NotNull @Valid @RequestParam("file") MultipartFile file,
      @NotEmpty @PathVariable("externalMovieId") String externalMovieId);

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{externalMovieId}/_trailer", method = RequestMethod.GET)
  Resource downloadTrailer(@NotEmpty @PathVariable("externalMovieId") String externalMovieId);
}
