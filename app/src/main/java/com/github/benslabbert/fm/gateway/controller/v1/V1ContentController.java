package com.github.benslabbert.fm.gateway.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequest;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponse;
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
  public InputStream get(@PathVariable String id) {
    return contentService.get(id);
  }

  @Post
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public HttpResponse<ContentUploadResponse> upload(
      @Part("image") CompletedFileUpload file, @Part("meta") String uploadRequestRaw)
      throws JsonProcessingException {

    try {
      log.info("upload request {}", uploadRequestRaw);
      var uploadRequest =
          objectMapper.readValue(uploadRequestRaw, new TypeReference<ContentUploadRequest>() {});
      log.info("upload request {}", uploadRequest);
      log.info("file name {} filename {}", file.getName(), file.getFilename());
    } finally {
      file.discard();
    }

    return HttpResponse.created(ContentUploadResponse.builder().build());
  }
}
