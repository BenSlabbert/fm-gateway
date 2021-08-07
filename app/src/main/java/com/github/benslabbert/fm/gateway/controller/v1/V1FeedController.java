package com.github.benslabbert.fm.gateway.controller.v1;

import com.github.benslabbert.fm.gateway.dto.v1.FeedRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.FeedResponseDto;
import com.github.benslabbert.fm.gateway.service.FeedService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@ExecuteOn(TaskExecutors.IO)
@Controller("/v1/feed")
@RequiredArgsConstructor
public class V1FeedController {

  private final FeedService feedService;

  @Get(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<FeedResponseDto> get(@Body @Valid FeedRequestDto feedRequestDto) {
    return HttpResponse.ok(feedService.get(feedRequestDto));
  }
}
