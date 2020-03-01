package com.digitalindexing.metamovie.datastore.api.model;

import com.digitalindexing.metamovie.datastore.api.model.aux.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;
import org.bson.types.ObjectId;
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
@Document(collection = Movie.MOVIES_COLLECTION)
public class Movie {

  @Transient public static final String MOVIES_COLLECTION = "movies";

  @Transient public static final String EXTERNAL_ID_FIELD = "externalMovieId";
  @Transient public static final String TITLE_FIELD = "title";
  @Transient public static final String IMDB_ID_FIELD = "imdbId";
  @Transient public static final String LANGUAGE_FIELD = "language";
  @Transient public static final String ADULT_FIELD = "adult";
  @Transient public static final String COLLECTION_FIELD = "collection";
  @Transient public static final String BUDGET_FIELD = "budget";
  @Transient public static final String GENRES_FIELD = "genres";
  @Transient public static final String HOMEPAGE_FIELD = "homepage";
  @Transient public static final String ORIGINAL_LANGUAGE_FIELD = "originalLanguage";
  @Transient public static final String ORIGINAL_TITLE_FIELD = "originalTitle";
  @Transient public static final String DESCRIPTION_FIELD = "description";
  @Transient public static final String PRODUCTION_COMPANIES_FIELD = "productionCompanies";
  @Transient public static final String PRODUCTION_COUNTRIES_FIELD = "productionCountries";
  @Transient public static final String RELEASE_DATE_FIELD = "releaseDate";
  @Transient public static final String RUNTIME_MINUTES_FIELD = "runtimeMinutes";
  @Transient public static final String REVENUE_FIELD = "revenue";
  @Transient public static final String STATUS_FIELD = "status";
  @Transient public static final String RATINGS_FIELD = "ratings";
  @Transient public static final String MOVIEDB_POPULARITY_FIELD = "theMovieDbPopularity";
  @Transient public static final String ACTORS_FIELD = "actors";
  @Transient public static final String PRODUCER_FIELD = "producers";
  @Transient public static final String TRANSLATION_DATA_FIELD = "translationData";

  @Transient public static final String DEFAULT_POSTER_URL_FIELD = "defaultPosterUrl";
  @Transient public static final String DEFAULT_POSTER_FILE_ID_FIELD = "defaultPosterFileId";
  @Transient public static final String DEFAULT_TRAILER_URL_FIELD = "defaultTrailerUrl";
  @Transient public static final String DEFAULT_TRAILER_FILE_ID_FIELD = "defaultTrailerFileId";

  @Transient public static final String SOURCE_SET_FIELD = "sourceSet";

  @EqualsAndHashCode.Include @JsonIgnore @Id private String id;

  @JsonProperty(EXTERNAL_ID_FIELD)
  @Indexed(unique = true)
  @Field(EXTERNAL_ID_FIELD)
  private String externalMovieId;

  @JsonProperty(IMDB_ID_FIELD)
  @Indexed(unique = true)
  @Field(IMDB_ID_FIELD)
  private String imdbId;

  @JsonProperty(TITLE_FIELD)
  @Field(TITLE_FIELD)
  private String title;

  @JsonProperty(DESCRIPTION_FIELD)
  @Field(DESCRIPTION_FIELD)
  private String description;

  @JsonProperty(GENRES_FIELD)
  @Field(GENRES_FIELD)
  private List<Genre> genreList;

  @JsonProperty(RELEASE_DATE_FIELD)
  @Field(RELEASE_DATE_FIELD)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate releaseDate;

  @JsonProperty(RUNTIME_MINUTES_FIELD)
  @Field(RUNTIME_MINUTES_FIELD)
  private Integer runtimeMinutes;

  @JsonProperty(ADULT_FIELD)
  @Field(ADULT_FIELD)
  private Boolean adult;

  @JsonProperty(ORIGINAL_LANGUAGE_FIELD)
  @Field(ORIGINAL_LANGUAGE_FIELD)
  private String originalLanguage;

  @JsonProperty(ORIGINAL_TITLE_FIELD)
  @Field(ORIGINAL_TITLE_FIELD)
  private String originalTitle;

  @JsonProperty(ACTORS_FIELD)
  @Field(ACTORS_FIELD)
  private List<Person> actors;

  @JsonProperty(PRODUCER_FIELD)
  @Field(PRODUCER_FIELD)
  private List<Person> producers;

  @JsonProperty(COLLECTION_FIELD)
  @Field(COLLECTION_FIELD)
  private Collection collection;

  @JsonProperty(BUDGET_FIELD)
  @Field(BUDGET_FIELD)
  private Long budget;

  @JsonProperty(REVENUE_FIELD)
  @Field(REVENUE_FIELD)
  private Long revenue;

  @JsonProperty(PRODUCTION_COMPANIES_FIELD)
  @Field(PRODUCTION_COMPANIES_FIELD)
  private List<Company> productionCompanies;

  @JsonProperty(PRODUCTION_COUNTRIES_FIELD)
  @Field(PRODUCTION_COUNTRIES_FIELD)
  private List<Country> productionCountries;

  @JsonProperty(RATINGS_FIELD)
  @Field(RATINGS_FIELD)
  private List<Rating> ratingList;

  @JsonProperty(MOVIEDB_POPULARITY_FIELD)
  @Field(MOVIEDB_POPULARITY_FIELD)
  private Double theMovieDbPopularity;

  @JsonProperty(STATUS_FIELD)
  @Field(STATUS_FIELD)
  private Status status;

  @JsonProperty(HOMEPAGE_FIELD)
  @Field(HOMEPAGE_FIELD)
  private String homepage;

  @JsonProperty(TRANSLATION_DATA_FIELD)
  @Field(TRANSLATION_DATA_FIELD)
  private List<TranslationData> translationDataList = new ArrayList<>();

  @JsonProperty(DEFAULT_POSTER_URL_FIELD)
  @Field(DEFAULT_POSTER_URL_FIELD)
  private String defaultPosterUrl;

  @JsonIgnore
  @Field(DEFAULT_POSTER_FILE_ID_FIELD)
  private ObjectId defaultPosterFileId;

  @JsonProperty(DEFAULT_TRAILER_URL_FIELD)
  @Field(DEFAULT_TRAILER_URL_FIELD)
  private String defaultTrailerUrl;

  @JsonIgnore
  @Field(DEFAULT_TRAILER_FILE_ID_FIELD)
  private ObjectId defaultTrailerId;

  @JsonIgnore
  @DBRef(lazy = true)
  @Field(SOURCE_SET_FIELD)
  private Set<Source> sourceSet = new HashSet<>();
}
