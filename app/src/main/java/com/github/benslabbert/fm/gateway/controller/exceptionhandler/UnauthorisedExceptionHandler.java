package com.github.benslabbert.fm.gateway.controller.exceptionhandler;

import com.github.benslabbert.fm.gateway.dto.UnauthorisedDto;
import com.github.benslabbert.fm.gateway.exception.UnauthorisedException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Produces
@Singleton
@Requires(classes = {UnauthorisedException.class})
public class UnauthorisedExceptionHandler
    implements ExceptionHandler<UnauthorisedException, HttpResponse<UnauthorisedDto>> {

  @Override
  public HttpResponse<UnauthorisedDto> handle(
      HttpRequest request, UnauthorisedException exception) {
    log.error("unauthorised request from user", exception);
    return HttpResponse.unauthorized()
        .body(UnauthorisedDto.builder().message("Failed to process request").build());
  }
}
