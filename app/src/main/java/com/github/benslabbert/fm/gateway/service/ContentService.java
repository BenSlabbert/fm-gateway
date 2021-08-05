package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequest;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponse;
import io.micronaut.http.multipart.CompletedFileUpload;
import java.io.InputStream;

public interface ContentService {

  InputStream get(String id);

  ContentUploadResponse put(ContentUploadRequest uploadRequest, CompletedFileUpload file);
}
