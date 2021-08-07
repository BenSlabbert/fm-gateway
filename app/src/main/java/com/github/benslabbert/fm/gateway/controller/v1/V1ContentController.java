package com.github.benslabbert.fm.gateway.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponseDto;
import com.github.benslabbert.fm.gateway.service.ContentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExecuteOn(TaskExecutors.IO)
@Controller("/v1/content")
@RequiredArgsConstructor
public class V1ContentController {

  private final ContentService contentService;
  private final ObjectMapper objectMapper;

  @Get("/{id}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public HttpResponse<InputStream> get(@PathVariable String id) {
    return HttpResponse.ok(contentService.get(id));
  }

  @Post
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public HttpResponse<ContentUploadResponseDto> upload(
      @Part("image") CompletedFileUpload file, @Part("meta") String uploadRequestRaw)
      throws JsonProcessingException {

    // todo we need to add security now and user auth to get the user details from the cookie etc...
    try {
      var uploadRequest =
          objectMapper.readValue(uploadRequestRaw, new TypeReference<ContentUploadRequestDto>() {});

      var resp = contentService.put(UUID.randomUUID(), uploadRequest, file);
      return HttpResponse.created(resp);
    } finally {
      file.discard();
    }
  }
}
