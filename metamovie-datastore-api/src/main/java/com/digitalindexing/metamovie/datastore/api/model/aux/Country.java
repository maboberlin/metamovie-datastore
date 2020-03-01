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
public class Country {
  @Transient public static final String ISO_3166_ID_FIELD = "iso3166Id";
  @Transient public static final String COUNTRY_NAME_FIELD = "countryName";

  @JsonProperty(ISO_3166_ID_FIELD)
  @Field(ISO_3166_ID_FIELD)
  private String iso3166Id;

  @JsonProperty(COUNTRY_NAME_FIELD)
  @Field(COUNTRY_NAME_FIELD)
  private String countryName;
}
