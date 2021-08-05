package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.config.MinioConfig;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequest;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponse;
import com.github.benslabbert.fm.gateway.exception.InternalServiceException;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.InputStream;
import java.util.UUID;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ContentServiceImpl implements ContentService {

  private static final String BUCKET = "content";
  private final MinioClient minioClient;

  @SneakyThrows
  public ContentServiceImpl(MinioConfig minioConfig) {
    this.minioClient =
        MinioClient.builder()
            .endpoint("http://" + minioConfig.getHost() + ":" + minioConfig.getPort())
            .credentials(minioConfig.getUsername(), minioConfig.getPassword())
            .build();

    var exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET).build());
    if (!exists) {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET).build());
    }
  }

  @Override
  public InputStream get(String id) {
    try {
      return minioClient.getObject(GetObjectArgs.builder().bucket(BUCKET).object(id).build());
    } catch (Exception e) {
      throw new InternalServiceException(e);
    }
  }

  @Override
  public ContentUploadResponse put(ContentUploadRequest uploadRequest, CompletedFileUpload file) {
    try {
      var objectId = UUID.randomUUID().toString();
      var putObjectArgs =
          PutObjectArgs.builder().bucket(BUCKET).object(objectId).stream(
                  file.getInputStream(), file.getSize(), -1)
              .contentType(
                  file.getContentType().orElse(MediaType.APPLICATION_OCTET_STREAM_TYPE).toString())
              .build();

      var objectWriteResponse = minioClient.putObject(putObjectArgs);
      return ContentUploadResponse.builder().id(objectId).etag(objectWriteResponse.etag()).build();
    } catch (Exception e) {
      throw new InternalServiceException(e);
    }
  }
}
