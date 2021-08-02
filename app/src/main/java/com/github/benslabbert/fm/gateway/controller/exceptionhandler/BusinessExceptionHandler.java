package com.github.benslabbert.fm.gateway.controller.exceptionhandler;

import com.github.benslabbert.fm.gateway.dto.BadRequest;
import com.github.benslabbert.fm.gateway.exception.BusinessException;
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
@Requires(classes = {BusinessException.class})
public class BusinessExceptionHandler
    implements ExceptionHandler<BusinessException, HttpResponse<BadRequest>> {

  @Override
  public HttpResponse<BadRequest> handle(HttpRequest request, BusinessException exception) {
    log.error("business exception occurred", exception);
    return HttpResponse.badRequest(
        BadRequest.builder().message("Failed to process request").build());
  }
}
