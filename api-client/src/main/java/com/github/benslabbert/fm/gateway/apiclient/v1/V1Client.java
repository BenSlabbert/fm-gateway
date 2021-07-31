package com.github.benslabbert.fm.gateway.apiclient.v1;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LoginResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import java.net.URL;

public class V1Client {

  private final HttpClient httpClient;

  public V1Client(URL url) {
    this.httpClient = HttpClient.create(url);
  }

  public LoginResponse login() {
    var login = UriBuilder.of("/v1").path("login").build();
    var post =
        HttpRequest.POST(
            login, LoginRequest.builder().username("user").password("password").build());
    return httpClient.toBlocking().retrieve(post, LoginResponse.class);
  }
}
