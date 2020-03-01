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
public class Collection {
  @Transient public static final String COLLECTION_ID = "collectionId";
  @Transient public static final String COLLECTION_NAME = "collectionName";

  @JsonProperty(COLLECTION_ID)
  @Field(COLLECTION_ID)
  private Integer collectionId;

  @JsonProperty(COLLECTION_NAME)
  @Field(COLLECTION_NAME)
  private String collectionName;
}
