package com.github.benslabbert.fm.gateway.service;

import java.io.InputStream;

public interface ContentService {

  InputStream get(String id);
}
