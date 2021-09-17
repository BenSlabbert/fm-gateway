package com.github.benslabbert.fm.gateway.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponseDto;
import com.github.benslabbert.fm.gateway.exception.UnauthorisedException;
import com.github.benslabbert.fm.gateway.grpc.IamClient;
import com.github.benslabbert.fm.gateway.service.ContentService;
import com.github.benslabbert.fm.iam.proto.service.v1.TokenValidRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
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
  private final IamClient iamClient;

  @Get("/{id}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public HttpResponse<InputStream> get(@Header("token") String token, @PathVariable String id) {
    var tokenValid = iamClient.isTokenValid(TokenValidRequest.newBuilder().setToken(token).build());
    if (!tokenValid.getValid()) {
      throw new UnauthorisedException();
    }

    return HttpResponse.ok(contentService.get(id));
  }

  @Post
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public HttpResponse<ContentUploadResponseDto> upload(
      @Header("token") String token,
      @Part("image") CompletedFileUpload file,
      @Part("meta") String uploadRequestRaw)
      throws JsonProcessingException {

    var tokenValid = iamClient.isTokenValid(TokenValidRequest.newBuilder().setToken(token).build());
    if (!tokenValid.getValid()) {
      throw new UnauthorisedException();
    }

    try {
      var dto =
          objectMapper.readValue(uploadRequestRaw, new TypeReference<ContentUploadRequestDto>() {});
      return HttpResponse.created(contentService.put(UUID.randomUUID(), dto, file));
    } finally {
      file.discard();
    }
  }
}
