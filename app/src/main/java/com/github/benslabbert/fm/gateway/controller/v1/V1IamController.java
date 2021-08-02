package com.github.benslabbert.fm.gateway.controller.v1;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LoginResponse;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponse;
import com.github.benslabbert.fm.gateway.service.IamService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@ExecuteOn(TaskExecutors.IO)
@Controller("/v1/iam")
@RequiredArgsConstructor
public class V1IamController {

  private final IamService iamService;

  @Post(uri = "login", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<LoginResponse> login(@Body @Valid LoginRequest loginRequest) {
    var resp = iamService.login(loginRequest);
    return HttpResponse.created(LoginResponse.builder().token(resp.getToken()).build());
  }

  @Post(
      uri = "logout",
      produces = MediaType.APPLICATION_JSON,
      consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<LogoutResponse> logout(@Body @Valid LogoutRequest logoutRequest) {
    var resp = iamService.logout(logoutRequest);
    return HttpResponse.created(resp);
  }
}
