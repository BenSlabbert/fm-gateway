package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.FeedItemDto;
import com.github.benslabbert.fm.gateway.dto.v1.FeedRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.FeedResponseDto;
import java.util.Collections;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

  @Override
  public FeedResponseDto get(FeedRequestDto feedRequestDto) {
    return FeedResponseDto.builder()
        .items(
            Collections.singletonList(
                FeedItemDto.builder()
                    .id("id")
                    .title("title")
                    .caption("caption")
                    .contentUri("some-minio-uri")
                    .build()))
        .build();
  }
}
