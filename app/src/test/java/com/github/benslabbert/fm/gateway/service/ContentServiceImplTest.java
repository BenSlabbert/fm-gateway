package com.github.benslabbert.fm.gateway.service;

import static org.junit.jupiter.api.Assertions.*;

import com.github.benslabbert.fm.gateway.config.MinioConfig;
import com.github.benslabbert.fm.gateway.dto.v1.ContentUploadRequest;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.netty.handler.codec.http.multipart.MemoryFileUpload;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

class ContentServiceImplTest {

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

    var contentService = new ContentServiceImpl(minioConfig);
    var fileUpload =
        new MemoryFileUpload("test", "test", "plain/text", "UTF-8", StandardCharsets.UTF_8, 4L);
    fileUpload.setContent(new ByteArrayInputStream("data".getBytes(StandardCharsets.UTF_8)));
    var put =
        contentService.put(
            ContentUploadRequest.builder().build(), new NettyCompletedFileUpload(fileUpload));
    assertNotNull(put);

    var inputStream = contentService.get(put.getId());
    var bytes = new byte[4];
    IOUtils.readFully(inputStream, bytes);
    assertEquals("data", new String(bytes, StandardCharsets.UTF_8));
  }
}