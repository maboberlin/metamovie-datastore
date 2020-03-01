package com.digitalindexing.metamovie.datastore.impl.service.impl;

import com.digitalindexing.metamovie.datastore.api.messaging.PosterDataMessageElement;
import com.digitalindexing.metamovie.datastore.api.model.Movie;
import com.digitalindexing.metamovie.datastore.api.model.Source;
import com.digitalindexing.metamovie.datastore.api.model.aux.PosterData;
import com.digitalindexing.metamovie.datastore.impl.exception.EntityNotFoundException;
import com.digitalindexing.metamovie.datastore.impl.repository.GridFSRepository;
import com.digitalindexing.metamovie.datastore.impl.repository.MovieRepository;
import com.digitalindexing.metamovie.datastore.impl.repository.impl.MovieCustomRepositoryImpl;
import com.digitalindexing.metamovie.datastore.impl.service.MovieService;
import com.digitalindexing.metamovie.datastore.impl.util.ContentTypeMappings;
import com.digitalindexing.metamovie.datastore.impl.util.FilenameCreationService;
import com.digitalindexing.metamovie.datastore.impl.util.IDGenerationService;
import com.mongodb.BasicDBObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

  public static final String DEFAULT_LANGUAGE = "en";

  @Autowired private GridFSRepository gridFSRepository;

  @Autowired private IDGenerationService idGenerationService;

  @Autowired private MovieRepository movieRepository;

  @Autowired private MovieCustomRepositoryImpl movieCustomRepositoryImpl;

  @Autowired private FilenameCreationService filenameCreationService;

  @Override
  public List<Movie> getAllMovies(Integer limit, Integer offset) {
    Pageable pageable = PageRequest.of(offset, limit);
    Page<Movie> all = movieRepository.findAll(pageable);
    return all.getContent();
  }

  @Override
  public Movie findMovieByExternalId(String externalMovieId) {
    return movieRepository
        .findByExternalMovieId(externalMovieId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    Movie.class.getSimpleName(),
                    String.format("Movie not found for external id '%s'", externalMovieId)));
  }

  @Override
  public List<Movie> findMovieByExternalIdList(Set<String> idList) {
    return IteratorUtils.toList(movieRepository.findByExternalMovieIdIn(idList).iterator());
  }

  @Override
  public Optional<Movie> findMovieByImdbId(String imdbId) {
    return movieRepository.findByImdbId(imdbId);
  }

  @Override
  public Movie updateMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  @Override
  public void addSource(Movie movie, Source source) {
    movie.getSourceSet().add(source);
    movieRepository.save(movie);
  }

  @Override
  public void removeSource(Source source) {
    movieCustomRepositoryImpl.removeSource(source.getId());
  }

  @Override
  public Movie saveMovie(Movie movie) {
    initializeMovieIdFields(movie);
    return movieRepository.save(movie);
  }

  @Override
  public void saveMovieBulk(List<Movie> movieList) {
    movieList.forEach(this::initializeMovieIdFields);
    movieRepository.saveAll(movieList);
  }

  @Override
  @SneakyThrows
  public void saveDefaultImage(MultipartFile file, String externalMovieId) {
    Movie movie = this.findMovieByExternalId(externalMovieId);

    String fileName =
        filenameCreationService.buildPosterFileName(
            externalMovieId, DEFAULT_LANGUAGE, file.getOriginalFilename());

    byte[] bytes = file.getBytes();

    ObjectId objectId = storeFile(bytes, externalMovieId, fileName, file.getContentType());

    movie.setDefaultPosterFileId(objectId);
    updateMovie(movie);
  }

  @Override
  public Resource getDefaultImage(String externalMovieId) {
    Movie movie = this.findMovieByExternalId(externalMovieId);

    String imageId = movie.getDefaultPosterFileId().toString();

    return getResource(externalMovieId, imageId);
  }

  @Override
  @SneakyThrows
  public void saveDefaultTrailer(MultipartFile file, String externalMovieId) {
    Movie movie = this.findMovieByExternalId(externalMovieId);

    String fileName =
        filenameCreationService.buildTrailerFileName(
            externalMovieId, DEFAULT_LANGUAGE, file.getOriginalFilename());

    byte[] bytes = file.getBytes();

    ObjectId objectId = storeFile(bytes, externalMovieId, fileName, file.getContentType());

    movie.setDefaultTrailerId(objectId);
    updateMovie(movie);
  }

  @Override
  public Resource getDefaultTrailer(String externalMovieId) {
    Movie movie = this.findMovieByExternalId(externalMovieId);

    String trailerId = movie.getDefaultTrailerId().toString();

    return getResource(externalMovieId, trailerId);
  }

  @Override
  public boolean saveMoviePosterData(PosterDataMessageElement messageElement, Movie movie) {
    String posterUrl = messageElement.getPosterData().getPosterUrl();
    String filename =
        filenameCreationService.buildPosterFileName(
            movie.getExternalMovieId(), messageElement.getLanguageIso639(), posterUrl);
    byte[] contentBase64Decoded = Base64.getDecoder().decode(messageElement.getContentBase64());
    String contentType =
        ContentTypeMappings.getContentTypeForFileSuffix(FilenameUtils.getExtension(filename));

    ObjectId fileId =
        this.storeFile(contentBase64Decoded, movie.getExternalMovieId(), filename, contentType);

    PosterData posterData =
        new PosterData(
            fileId,
            posterUrl,
            messageElement.getPosterData().getPosterHeight(),
            messageElement.getPosterData().getPosterWidth());

    return movieRepository.updatePosterData(movie, messageElement.getLanguageIso639(), posterData);
  }

  private void initializeMovieIdFields(Movie movie) {
    movie.setId(null);
    movie.setExternalMovieId(idGenerationService.generateID());
  }

  private ObjectId storeFile(
      byte[] content, String externalMovieId, String fileName, String contentType) {
    try (InputStream in = new ByteArrayInputStream(content)) {
      return gridFSRepository.storeAndOverwrite(in, fileName, contentType, new BasicDBObject());
    } catch (Exception e) {
      log.error(
          "Error storing file '{}' to grid fs for movie with external id '{}'",
          fileName,
          externalMovieId,
          e);
      throw new RuntimeException(e);
    }
  }

  private Resource getResource(String externalMovieId, String imageId) {
    try {
      GridFsResource gridFsResource = gridFSRepository.getResourceById(imageId);
      if (gridFsResource == null || !gridFsResource.exists()) {
        throw new EntityNotFoundException(
            GridFsResource.class.getSimpleName(),
            String.format(
                "File '%s' not found for movie with external id '%s'", imageId, externalMovieId));
      }

      return new ByteArrayResource(IOUtils.toByteArray(gridFsResource.getInputStream()));
    } catch (EntityNotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error(
          "Error retrieving file '{}' from grid fs for movie with external id '{}'",
          imageId,
          externalMovieId,
          e);
      throw new RuntimeException(e);
    }
  }
}
