package com.digitalindexing.metamovie.datastore.impl.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {
  String IMDB_INITIALIZATION_INPUT = "imdb-initialization-kafka";

  String IMDB_POSTER_INPUT = "imdb-posters-kafka";

  @Input(KafkaChannels.IMDB_INITIALIZATION_INPUT)
  MessageChannel imdbInitializationInput();

  @Input(KafkaChannels.IMDB_POSTER_INPUT)
  MessageChannel imdbPosterInput();
}
