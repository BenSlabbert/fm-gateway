package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LoginResponseDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponseDto;
import com.github.benslabbert.fm.gateway.dto.v1.RefreshRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.RefreshResponseDto;
import com.github.benslabbert.fm.gateway.grpc.IamClient;
import com.github.benslabbert.fm.iam.proto.service.v1.LogoutRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.RefreshRequest;
import com.google.protobuf.ByteString;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class IamService {

  private final IamClient iamClient;

  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    var resp =
        iamClient.login(
            com.github.benslabbert.fm.iam.proto.service.v1.LoginRequest.newBuilder()
                .setName(loginRequestDto.getUsername())
                .setPassword(ByteString.copyFromUtf8(loginRequestDto.getPassword()))
                .build());

    return LoginResponseDto.builder()
        .token(resp.getToken())
        .refreshToken(resp.getRefreshToken())
        .build();
  }

  public LogoutResponseDto logout(LogoutRequestDto logoutRequestDto) {
    iamClient.logout(LogoutRequest.newBuilder().setToken(logoutRequestDto.getToken()).build());
    return new LogoutResponseDto();
  }

  public RefreshResponseDto refresh(RefreshRequestDto refreshRequestDto) {
    var resp =
        iamClient.refresh(
            RefreshRequest.newBuilder()
                .setRefreshToken(refreshRequestDto.getRefreshToken())
                .build());
    return RefreshResponseDto.builder()
        .token(resp.getToken())
        .refreshToken(resp.getRefreshToken())
        .build();
  }
}
