package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.FeedRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.FeedResponseDto;

public interface FeedService {

  FeedResponseDto get(FeedRequestDto feedRequestDto);
}
