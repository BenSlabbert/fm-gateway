package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponse;
import com.github.benslabbert.fm.gateway.grpc.IamClient;
import com.github.benslabbert.fm.iam.proto.LoginResponse;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class IamServiceImpl implements IamService {

  private final IamClient iamClient;

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    return iamClient.send(
        com.github.benslabbert.fm.iam.proto.LoginRequest.newBuilder()
            .setName(loginRequest.getUsername())
            .setPassword(loginRequest.getPassword())
            .build());
  }

  @Override
  public LogoutResponse logout(LogoutRequest logoutRequest) {
    return new LogoutResponse();
  }
}
