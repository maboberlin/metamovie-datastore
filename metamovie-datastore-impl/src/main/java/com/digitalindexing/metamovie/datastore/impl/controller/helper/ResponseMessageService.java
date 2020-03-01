package com.digitalindexing.metamovie.datastore.impl.controller.helper;

import com.digitalindexing.metamovie.datastore.impl.controller.dtos.Message;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ResponseMessageService {

  @Value("${spring.application.name}")
  private String appName;

  public Message buildResponseMessage(String msg) {
    String path =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
            .getRequest()
            .getRequestURI();
    return Message.builder()
        .message(msg)
        .timestamp(OffsetDateTime.now())
        .path(path)
        .service(appName)
        .build();
  }
}
