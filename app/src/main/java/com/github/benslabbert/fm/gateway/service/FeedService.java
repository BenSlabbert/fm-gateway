package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.FeedRequest;
import com.github.benslabbert.fm.gateway.dto.v1.FeedResponse;

public interface FeedService {

  FeedResponse get(FeedRequest feedRequest);
}
