package com.digitalindexing.metamovie.datastore.api.model.aux;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
  @Transient public static final String GENRE_ID_FIELD = "genreId";
  @Transient public static final String GENRE_NAME_FIELD = "genreName";

  @JsonProperty(GENRE_ID_FIELD)
  @Field(GENRE_ID_FIELD)
  private Integer genreId;

  @JsonProperty(GENRE_NAME_FIELD)
  @Field(GENRE_NAME_FIELD)
  private String genreName;
}
