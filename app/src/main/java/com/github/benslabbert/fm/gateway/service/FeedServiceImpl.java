package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.FeedItem;
import com.github.benslabbert.fm.gateway.dto.v1.FeedRequest;
import com.github.benslabbert.fm.gateway.dto.v1.FeedResponse;
import java.util.Collections;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

  @Override
  public FeedResponse get(FeedRequest feedRequest) {
    return FeedResponse.builder()
        .items(
            Collections.singletonList(
                FeedItem.builder()
                    .id("id")
                    .title("title")
                    .caption("caption")
                    .contentUri("some-minio-uri")
                    .build()))
        .build();
  }
}
