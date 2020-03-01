package com.digitalindexing.metamovie.datastore.api.messaging;

import com.digitalindexing.metamovie.datastore.api.model.aux.PosterData;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PosterDataMessageElement {
  private String imdbId;
  private String languageIso639;
  private byte[] contentBase64;
  private PosterData posterData;
}
