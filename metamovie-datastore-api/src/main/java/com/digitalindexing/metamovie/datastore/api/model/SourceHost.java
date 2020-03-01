package com.digitalindexing.metamovie.datastore.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
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
@Document(collection = SourceHost.SOURCE_HOSTS)
public class SourceHost {
  @Transient public static final String SOURCE_HOSTS = "source_hosts";

  @Transient public static final String SOURCE_SET_FIELD = "sourceSet";
  @Transient public static final String EXTERNAL_ID_FIELD = "externalSourceHostId";
  @Transient public static final String NAME_FIELD = "name";
  @Transient public static final String DOMAIN_URL_FIELD = "domainUrl";

  @EqualsAndHashCode.Include @JsonIgnore @Id private String id;

  @JsonProperty("sourceHostId")
  @Indexed(unique = true)
  @Field(EXTERNAL_ID_FIELD)
  private String externalSourceHostId;

  @JsonProperty("domainUrl")
  @Indexed(unique = true)
  @Field(DOMAIN_URL_FIELD)
  private String domainUrl;

  @JsonProperty("name")
  @Field(NAME_FIELD)
  private String name;

  @JsonIgnore
  @DBRef(lazy = true)
  @Field(SOURCE_SET_FIELD)
  private Set<Source> sourceSet = new HashSet<>();
}
