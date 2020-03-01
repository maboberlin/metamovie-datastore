package com.digitalindexing.metamovie.datastore.api.model.aux;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslationData {
  @Transient public static final String TRANSLATION_ISO_639_ID_FIELD = "iso639Id";
  @Transient public static final String TRANSLATION_COUNTRY_NAME_FIELD = "countryName";
  @Transient public static final String TRANSLATION_TITLE_FIELD = "title";
  @Transient public static final String TRANSLATION_DESCRIPTION_FIELD = "description";
  @Transient public static final String TRANSLATION_TRAILER_URL_FIELD = "trailerUrl";
  @Transient public static final String TRANSLATION_TRAILER_FILE_ID_FIELD = "trailer";
  @Transient public static final String POSTER_DATA_FIELD = "posterData";

  @JsonProperty(TRANSLATION_ISO_639_ID_FIELD)
  @Field(TRANSLATION_ISO_639_ID_FIELD)
  private String iso639Id;

  @JsonProperty(TRANSLATION_COUNTRY_NAME_FIELD)
  @Field(TRANSLATION_COUNTRY_NAME_FIELD)
  private String countryName;

  @JsonProperty(TRANSLATION_TITLE_FIELD)
  @Field(TRANSLATION_TITLE_FIELD)
  private String title;

  @JsonProperty(TRANSLATION_DESCRIPTION_FIELD)
  @Field(TRANSLATION_DESCRIPTION_FIELD)
  private String description;

  @JsonProperty(POSTER_DATA_FIELD)
  @Field(POSTER_DATA_FIELD)
  private PosterData posterData;

  @JsonIgnore
  @Field(TRANSLATION_TRAILER_FILE_ID_FIELD)
  private ObjectId trailerId;

  @JsonProperty(TRANSLATION_TRAILER_URL_FIELD)
  @Field(TRANSLATION_TRAILER_URL_FIELD)
  private String trailerUrl;
}
