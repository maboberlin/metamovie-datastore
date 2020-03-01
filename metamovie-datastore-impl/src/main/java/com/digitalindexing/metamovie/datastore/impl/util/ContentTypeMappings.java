package com.digitalindexing.metamovie.datastore.impl.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContentTypeMappings {
  private static final Map<String, String> CONTENT_TYPE_MAPPINGS;

  static {
    Map<String, String> map = new HashMap<>();
    map.put("jpeg", "image/jpeg");
    map.put("jpg", "image/jpg");
    CONTENT_TYPE_MAPPINGS = Collections.unmodifiableMap(map);
  }

  public static String getContentTypeForFileSuffix(String fileSuffix) {
    if (fileSuffix == null) {
      return null;
    }
    return CONTENT_TYPE_MAPPINGS.get(fileSuffix.toLowerCase());
  }
}
