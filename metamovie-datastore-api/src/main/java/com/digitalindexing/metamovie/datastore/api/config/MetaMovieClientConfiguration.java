package com.digitalindexing.metamovie.datastore.api.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetaMovieClientConfiguration {
  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new ErrorDecoder.Default();
  }

  @Bean
  public OkHttpClient client() {
    return new OkHttpClient();
  }
}
