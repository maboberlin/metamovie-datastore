package com.digitalindexing.metamovie.datastore.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MetaMovieEntity
@Document(collection = Source.SOURCES_COLLECTION)
public class Source {

  @Transient public static final String SOURCES_COLLECTION = "sources";

  @Transient public static final String SOURCE_HOST_FIELD = "sourceHost";
  @Transient public static final String MOVIE_FIELD = "movie";
  @Transient public static final String EXTERNAL_ID_FIELD = "externalSourceId";
  @Transient public static final String URL_FIELD = "url";
  @Transient public static final String NAME_FIELD = "name";

  @EqualsAndHashCode.Include @JsonIgnore @Id private String id;

  @JsonProperty("sourceId")
  @Indexed(unique = true)
  @Field(EXTERNAL_ID_FIELD)
  private String externalSourceId;

  @JsonProperty("url")
  @Indexed(unique = true)
  @Field(URL_FIELD)
  private String url;

  @JsonProperty("name")
  @Field(NAME_FIELD)
  private String name;

  @JsonIgnore
  @DBRef(lazy = true)
  @Field(MOVIE_FIELD)
  private Movie movie;

  @JsonIgnore
  @DBRef(lazy = true)
  @Field(SOURCE_HOST_FIELD)
  private SourceHost sourceHost;
}
