package com.github.benslabbert.fm.gateway.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

  @Override
  public InputStream get(String id) {
    return new ByteArrayInputStream("data".getBytes(StandardCharsets.UTF_8));
  }
}
