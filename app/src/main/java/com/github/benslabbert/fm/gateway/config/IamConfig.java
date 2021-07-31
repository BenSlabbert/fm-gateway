package com.github.benslabbert.fm.gateway.config;

import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class IamConfig {

  @Value("${fm.iam.server.host:localhost}")
  private String host;

  @Value("${fm.iam.server.port:50052}")
  private Integer port;
}
