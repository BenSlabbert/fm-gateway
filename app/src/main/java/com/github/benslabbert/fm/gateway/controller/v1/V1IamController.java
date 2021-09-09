package com.github.benslabbert.fm.gateway.controller.v1;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LoginResponseDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponseDto;
import com.github.benslabbert.fm.gateway.dto.v1.RefreshRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.RefreshResponseDto;
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
  public HttpResponse<LoginResponseDto> login(@Body @Valid LoginRequestDto loginRequestDto) {
    var resp = iamService.login(loginRequestDto);
    return HttpResponse.created(resp);
  }

  @Post(
      uri = "logout",
      produces = MediaType.APPLICATION_JSON,
      consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<LogoutResponseDto> logout(@Body @Valid LogoutRequestDto logoutRequestDto) {
    var resp = iamService.logout(logoutRequestDto);
    return HttpResponse.ok(resp);
  }

  @Post(
      uri = "refresh",
      produces = MediaType.APPLICATION_JSON,
      consumes = MediaType.APPLICATION_JSON)
  public HttpResponse<RefreshResponseDto> refresh(
      @Body @Valid RefreshRequestDto refreshRequestDto) {
    var resp = iamService.refresh(refreshRequestDto);
    return HttpResponse.created(resp);
  }
}
