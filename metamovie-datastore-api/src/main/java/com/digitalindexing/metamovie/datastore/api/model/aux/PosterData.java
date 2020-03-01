package com.digitalindexing.metamovie.datastore.api.model.aux;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PosterData {

  @Transient public static final String POSTER_FILE_ID_FIELD = "posterFileId";
  @Transient public static final String POSTER_URL_FIELD = "posterUrl";
  @Transient public static final String POSTER_HEIGHT_FIELD = "posterHeight";
  @Transient public static final String POSTER_WIDTH_FIELD = "posterWidth";

  @JsonIgnore
  @Field(POSTER_FILE_ID_FIELD)
  private ObjectId posterFileId;

  @JsonProperty(POSTER_URL_FIELD)
  @Field(POSTER_URL_FIELD)
  private String posterUrl;

  @JsonProperty(POSTER_HEIGHT_FIELD)
  @Field(POSTER_HEIGHT_FIELD)
  private Integer posterHeight;

  @JsonProperty(POSTER_WIDTH_FIELD)
  @Field(POSTER_WIDTH_FIELD)
  private Integer posterWidth;
}
