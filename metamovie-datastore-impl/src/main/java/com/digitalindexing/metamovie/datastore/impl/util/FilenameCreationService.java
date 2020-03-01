package com.digitalindexing.metamovie.datastore.impl.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

@Service
public class FilenameCreationService {

  public String buildPosterFileName(
      String externalMovieId, String language, String originalFilename) {
    return String.format(
        "posterfile-%s-%s.%s",
        externalMovieId, language, FilenameUtils.getExtension(originalFilename));
  }

  public String buildTrailerFileName(
      String externalMovieId, String defaultLanguage, String originalFilename) {
    return String.format(
        "trailerfile-%s-%s.%s",
        externalMovieId, defaultLanguage, FilenameUtils.getExtension(originalFilename));
  }
}
