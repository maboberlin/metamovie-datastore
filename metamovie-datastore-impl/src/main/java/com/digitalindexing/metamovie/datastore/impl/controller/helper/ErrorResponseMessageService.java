package com.digitalindexing.metamovie.datastore.impl.controller.helper;

import com.digitalindexing.metamovie.datastore.impl.controller.dtos.ErrorMessage;
import com.digitalindexing.metamovie.datastore.impl.exception.EntityNotFoundException;
import com.digitalindexing.metamovie.datastore.impl.exception.MoreThanOneEntityFoundException;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorResponseMessageService extends ResponseEntityExceptionHandler {

  @Value("${spring.application.name}")
  private String appName;

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleMyException(
      EntityNotFoundException ex, WebRequest request) {
    String msg = String.format("%s not found. %s", ex.getTypeName(), ex.getMessage());
    return buildErrorResponse(msg, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MoreThanOneEntityFoundException.class)
  public ResponseEntity<ErrorMessage> handleMyException(
      MoreThanOneEntityFoundException ex, WebRequest request) {
    String msg = String.format("%s more than one found. %s", ex.getTypeName(), ex.getMessage());
    return buildErrorResponse(msg, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleMyException(Exception ex, WebRequest request) {
    String msg = ex.getMessage();
    return buildErrorResponse(msg, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorMessage> buildErrorResponse(String msg, HttpStatus status) {
    String path =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
            .getRequest()
            .getRequestURI();
    ErrorMessage errorMessage =
        ErrorMessage.builder()
            .error(status.getReasonPhrase())
            .message(msg)
            .status(status.value())
            .timestamp(OffsetDateTime.now())
            .path(path)
            .service(appName)
            .build();
    return new ResponseEntity<>(errorMessage, status);
  }
}
