package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponseDto;
import com.github.benslabbert.fm.gateway.grpc.IamClient;
import com.github.benslabbert.fm.iam.proto.service.v1.LoginResponse;
import com.google.protobuf.ByteString;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class IamServiceImpl implements IamService {

  private final IamClient iamClient;

  @Override
  public LoginResponse login(LoginRequestDto loginRequestDto) {
    return iamClient.send(
        com.github.benslabbert.fm.iam.proto.service.v1.LoginRequest.newBuilder()
            .setName(loginRequestDto.getUsername())
            .setPassword(ByteString.copyFromUtf8(loginRequestDto.getPassword()))
            .build());
  }

  @Override
  public LogoutResponseDto logout(LogoutRequestDto logoutRequestDto) {
    return new LogoutResponseDto();
  }
}
