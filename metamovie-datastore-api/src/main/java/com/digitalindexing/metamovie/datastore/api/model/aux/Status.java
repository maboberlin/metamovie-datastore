package com.digitalindexing.metamovie.datastore.api.model.aux;

public enum Status {
  RUMORED,
  RELEASED,
  PLANNED,
  IN_PRODUCTION,
  POST_PRODUCTION,
  CANCELED;

  public static Status forString(String txt) {
    for (Status status : Status.values()) {
      if (status.toString().equalsIgnoreCase(txt)) {
        return status;
      }
    }
    return null;
  }
}
