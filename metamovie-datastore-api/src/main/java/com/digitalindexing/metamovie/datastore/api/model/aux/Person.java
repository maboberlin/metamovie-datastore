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
public class Person {
  @Transient public static final String FIRST_NAME_FIELD = "firstName";
  @Transient public static final String LAST_NAME_FIELD = "lastName";

  @JsonProperty(FIRST_NAME_FIELD)
  @Field(FIRST_NAME_FIELD)
  private String firstName;

  @JsonProperty(LAST_NAME_FIELD)
  @Field(LAST_NAME_FIELD)
  private String lastName;
}
