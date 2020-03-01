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
public class Company {
  @Transient public static final String COMPANY_ID_FIELD = "companyId";
  @Transient public static final String COMPANY_COUNTRY_FIELD = "companyCountry";
  @Transient public static final String COMPANY_NAME_FIELD = "companyName";

  @JsonProperty(COMPANY_ID_FIELD)
  @Field(COMPANY_ID_FIELD)
  private Integer companyId;

  @JsonProperty(COMPANY_COUNTRY_FIELD)
  @Field(COMPANY_COUNTRY_FIELD)
  private String companyCountry;

  @JsonProperty(COMPANY_NAME_FIELD)
  @Field(COMPANY_NAME_FIELD)
  private String companyName;
}
