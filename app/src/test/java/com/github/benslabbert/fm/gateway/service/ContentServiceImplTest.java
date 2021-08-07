package com.github.benslabbert.fm.gateway.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.github.benslabbert.fm.gateway.config.MinioConfig;
import com.github.benslabbert.fm.gateway.dao.entity.Upload;
import com.github.benslabbert.fm.gateway.dao.repo.UploadRepo;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequestDto;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.netty.handler.codec.http.multipart.MemoryFileUpload;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@MicronautTest
class ContentServiceImplTest {

  @Inject UploadRepo uploadRepo;

  @MockBean(UploadRepo.class)
  UploadRepo mathService() {
    return mock(UploadRepo.class);
  }

  private GenericContainer<?> minioContainer;

  @BeforeEach
  void setUp() {
    minioContainer =
        new GenericContainer<>("minio/minio")
            .withCommand("server /data")
            .withExposedPorts(9000)
            .withEnv("MINIO_ROOT_USER", "minio")
            .withEnv("MINIO_ROOT_PASSWORD", "minio123")
            .waitingFor(Wait.forListeningPort());
    minioContainer.start();
  }

  @AfterEach
  void cleanUp() {
    minioContainer.stop();
  }

  @Test
  void test() throws IOException {
    var minioConfig =
        MinioConfig.builder()
            .host(minioContainer.getHost())
            .port(minioContainer.getMappedPort(9000))
            .username("minio")
            .password("minio123")
            .build();

    var contentService = new ContentServiceImpl(minioConfig, uploadRepo);
    var fileUpload =
        new MemoryFileUpload("test", "test", "plain/text", "UTF-8", StandardCharsets.UTF_8, 4L);
    fileUpload.setContent(new ByteArrayInputStream("data".getBytes(StandardCharsets.UTF_8)));
    var put =
        contentService.put(
            UUID.randomUUID(),
            ContentUploadRequestDto.builder().build(),
            new NettyCompletedFileUpload(fileUpload));
    assertNotNull(put);

    var inputStream = contentService.get(put.getId());
    var bytes = new byte[4];
    IOUtils.readFully(inputStream, bytes);
    assertEquals("data", new String(bytes, StandardCharsets.UTF_8));

    verify(uploadRepo).save(any(Upload.class));
  }
}
