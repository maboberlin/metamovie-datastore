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
public class Rating {
  @Transient public static final String RATING_FIELD = "rating";
  @Transient public static final String RATING_PLATFORM_FIELD = "ratingPlatform";

  @JsonProperty(RATING_FIELD)
  @Field(RATING_FIELD)
  private Double rating;

  @JsonProperty(RATING_PLATFORM_FIELD)
  @Field(RATING_PLATFORM_FIELD)
  private RatingPlatform ratingPlatform;
}
