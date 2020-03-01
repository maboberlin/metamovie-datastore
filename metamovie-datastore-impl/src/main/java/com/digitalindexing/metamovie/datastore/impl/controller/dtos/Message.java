package com.digitalindexing.metamovie.datastore.impl.controller.dtos;

import java.time.OffsetDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
  private OffsetDateTime timestamp;
  private Integer status;
  private String message;
  private String path;
  private String service;
}
