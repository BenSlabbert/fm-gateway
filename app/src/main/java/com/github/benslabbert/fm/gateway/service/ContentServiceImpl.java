package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.config.MinioConfig;
import com.github.benslabbert.fm.gateway.dao.entity.Upload;
import com.github.benslabbert.fm.gateway.dao.entity.UploadContentType;
import com.github.benslabbert.fm.gateway.dao.repo.UploadRepo;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadResponseDto;
import com.github.benslabbert.fm.gateway.dto.v1.UploadPageResponseDto;
import com.github.benslabbert.fm.gateway.exception.InternalServiceException;
import com.github.benslabbert.fm.gateway.exception.UnauthorisedException;
import com.github.benslabbert.fm.iam.proto.message.v1.UserMessage;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.InputStream;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ContentServiceImpl implements ContentService {

  private static final String BUCKET = "content";
  private final MinioClient minioClient;
  private final UploadRepo uploadRepo;

  @SneakyThrows
  public ContentServiceImpl(MinioConfig minioConfig, UploadRepo uploadRepo) {
    this.uploadRepo = uploadRepo;

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
  public InputStream get(UserMessage user, String objectKey) {
    var upload =
        uploadRepo.findByUserIdAndObjectKey(UUID.fromString(user.getId().getValue()), objectKey);

    if (upload.isEmpty()) {
      throw new UnauthorisedException();
    }

    try {
      return minioClient.getObject(
          GetObjectArgs.builder().bucket(BUCKET).object(upload.get().getObjectKey()).build());
    } catch (Exception e) {
      throw new InternalServiceException(e);
    }
  }

  @Override
  @Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
  public ContentUploadResponseDto put(
      UserMessage user, ContentUploadRequestDto uploadRequest, CompletedFileUpload file) {

    try {
      var objectId = UUID.randomUUID().toString();
      var putObjectArgs =
          PutObjectArgs.builder().bucket(BUCKET).object(objectId).stream(
                  file.getInputStream(), file.getSize(), -1)
              .contentType(
                  file.getContentType().orElse(MediaType.APPLICATION_OCTET_STREAM_TYPE).toString())
              .build();

      var objectWriteResponse = minioClient.putObject(putObjectArgs);

      uploadRepo.save(
          Upload.builder()
              .userId(UUID.fromString(user.getId().getValue()))
              .contentLength(file.getSize())
              .objectKey(objectId)
              .contentType(UploadContentType.IMAGE)
              .etag(objectWriteResponse.etag())
              .build());

      return ContentUploadResponseDto.builder()
          .id(objectId)
          .etag(objectWriteResponse.etag())
          .build();
    } catch (Exception e) {
      throw new InternalServiceException(e);
    }
  }

  @Override
  @Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
  public UploadPageResponseDto get(UserMessage user, Integer page, Integer size) {
    var p = uploadRepo.findAll(Pageable.from(page, size));

    return UploadPageResponseDto.builder()
        .page(p.getPageNumber())
        .size(p.getSize())
        .total(p.getTotalPages())
        .entries(
            p.getContent().stream()
                .map(
                    upload ->
                        ContentUploadResponseDto.builder()
                            .id(upload.getObjectKey())
                            .etag(upload.getEtag())
                            .build())
                .collect(Collectors.toList()))
        .build();
  }
}
