package com.digitalindexing.metamovie.datastore.impl.messaging;

import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.impl.config.MessagingChannelsConfig;
import com.digitalindexing.metamovie.datastore.impl.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Slf4j
@Validated
@EnableBinding(KafkaChannels.class)
public class IMDBInitializationKafkaListener {

  @Autowired private MovieService movieService;

  @StreamListener(KafkaChannels.IMDB_INITIALIZATION_INPUT)
  public void handleIMDBMovieInitializationMessage(
      @Payload Movie movie,
      @Header(MessagingChannelsConfig.IMDB_BASIC_IX_HEADER) Long ix,
      @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {
    log.debug(
        "Received movie data from kafka pipeline for initialization with index '{}' and with imdbId '{}'",
        ix,
        movie.getImdbId());
    if (movieService.findMovieByImdbId(movie.getImdbId()).isEmpty()) {
      Movie savedMovie = movieService.saveMovie(movie);
      log.debug("Saved movie with external ID '{}' to db", savedMovie.getExternalMovieId());
    } else {
      log.debug("Movie with imdbID '{}' already exists in db", movie.getImdbId());
    }
    acknowledgment.acknowledge();
  }
}
