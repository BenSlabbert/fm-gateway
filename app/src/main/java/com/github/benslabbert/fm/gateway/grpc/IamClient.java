package com.github.benslabbert.fm.gateway.grpc;

import com.github.benslabbert.fm.gateway.config.IamConfig;
import com.github.benslabbert.fm.iam.proto.service.v1.DeleteAccountRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.DeleteAccountResponse;
import com.github.benslabbert.fm.iam.proto.service.v1.IamServiceGrpc;
import com.github.benslabbert.fm.iam.proto.service.v1.LockAccountRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.LockAccountResponse;
import com.github.benslabbert.fm.iam.proto.service.v1.LoginRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.LoginResponse;
import com.github.benslabbert.fm.iam.proto.service.v1.LogoutRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.LogoutResponse;
import com.github.benslabbert.fm.iam.proto.service.v1.RefreshRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.RefreshResponse;
import com.github.benslabbert.fm.iam.proto.service.v1.TokenValidRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.TokenValidResponse;
import io.grpc.ManagedChannelBuilder;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class IamClient {

  private final IamServiceGrpc.IamServiceBlockingStub stub;

  public IamClient(IamConfig iamConfig) {
    var channel =
        ManagedChannelBuilder.forAddress(iamConfig.getHost(), iamConfig.getPort())
            .usePlaintext()
            .build();
    this.stub = IamServiceGrpc.newBlockingStub(channel);
  }

  public LoginResponse login(LoginRequest request) {
    return stub.login(request);
  }

  public LogoutResponse logout(LogoutRequest request) {
    return stub.logout(request);
  }

  public RefreshResponse refresh(RefreshRequest request) {
    return stub.refresh(request);
  }

  public TokenValidResponse isTokenValid(TokenValidRequest request) {
    return stub.tokenValid(request);
  }

  public DeleteAccountResponse delete(DeleteAccountRequest request) {
    return stub.deleteAccount(request);
  }

  public LockAccountResponse lock(LockAccountRequest request) {
    return stub.lockAccount(request);
  }
}
