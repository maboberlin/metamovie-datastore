package com.digitalindexing.metamovie.datastore.impl.exception;

import lombok.Getter;

public class MoreThanOneEntityFoundException extends RuntimeException {

  @Getter private String typeName;

  public MoreThanOneEntityFoundException(String typeName, String msg) {
    super(msg);
    this.typeName = typeName;
  }
}
