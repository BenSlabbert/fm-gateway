package com.github.benslabbert.fm.gateway.config;

import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Singleton
@NoArgsConstructor
@AllArgsConstructor
public class MinioConfig {

  @Value("${minio.server.host:localhost}")
  private String host;

  @Value("${minio.server.port:9000}")
  private Integer port;

  @Value("${minio.username:minio}")
  private String username;

  @Value("${minio.password:minio123}")
  private String password;
}
