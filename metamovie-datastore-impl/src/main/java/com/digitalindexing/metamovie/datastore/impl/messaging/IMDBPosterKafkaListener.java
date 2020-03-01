package com.digitalindexing.metamovie.datastore.impl.messaging;

import com.digitalindexing.metamovie.datastore.api.messaging.PosterDataMessageElement;
import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.impl.config.MessagingChannelsConfig;
import com.digitalindexing.metamovie.datastore.impl.service.MovieService;
import java.util.Optional;
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
public class IMDBPosterKafkaListener {

  @Autowired private MovieService movieService;

  @StreamListener(KafkaChannels.IMDB_POSTER_INPUT)
  public void handleIMDBMovieInitializationMessage(
      @Payload PosterDataMessageElement posterData,
      @Header(MessagingChannelsConfig.IMDB_BASIC_IX_HEADER) Long ix,
      @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {
    String imdbId = posterData.getImdbId();
    log.debug(
        "Received poster data from kafka pipeline with index '{}', imdbId '{}' and language '{}'",
        ix,
        imdbId,
        posterData.getLanguageIso639());

    Optional<Movie> movieOptional = movieService.findMovieByImdbId(imdbId);
    if (movieOptional.isEmpty()) {
      throw new IllegalStateException(
          String.format(
              "Error storing poster data to db. Movie with imdb ID '%s' not existing.", imdbId));
    }

    try {
      movieService.saveMoviePosterData(posterData, movieOptional.get());
    } finally {
      acknowledgment.acknowledge();
    }
  }
}
