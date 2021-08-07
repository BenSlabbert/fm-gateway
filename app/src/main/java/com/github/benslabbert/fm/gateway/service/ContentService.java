package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponseDto;
import io.micronaut.http.multipart.CompletedFileUpload;
import java.io.InputStream;
import java.util.UUID;

public interface ContentService {

  InputStream get(String id);

  ContentUploadResponseDto put(
      UUID userId, ContentUploadRequestDto uploadRequest, CompletedFileUpload file);
}
