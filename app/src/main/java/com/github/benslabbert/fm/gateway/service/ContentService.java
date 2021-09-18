package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponseDto;
import com.github.benslabbert.fm.gateway.dto.v1.UploadPageResponseDto;
import com.github.benslabbert.fm.iam.proto.message.v1.UserMessage;
import io.micronaut.http.multipart.CompletedFileUpload;
import java.io.InputStream;

public interface ContentService {

  InputStream get(UserMessage user, String objectKey);

  ContentUploadResponseDto put(
      UserMessage user, ContentUploadRequestDto uploadRequest, CompletedFileUpload file);

  UploadPageResponseDto get(UserMessage user, Integer page, Integer size);
}
