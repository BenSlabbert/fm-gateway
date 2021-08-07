package com.github.benslabbert.fm.gateway.grpc;

import com.github.benslabbert.fm.gateway.config.IamConfig;
import com.github.benslabbert.fm.iam.proto.service.v1.IamServiceGrpc;
import com.github.benslabbert.fm.iam.proto.service.v1.LoginRequest;
import com.github.benslabbert.fm.iam.proto.service.v1.LoginResponse;
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

  public LoginResponse send(LoginRequest request) {
    return stub.login(request);
  }
}
