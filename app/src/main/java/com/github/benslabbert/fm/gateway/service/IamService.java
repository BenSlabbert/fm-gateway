package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequest;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponse;
import com.github.benslabbert.fm.iam.proto.LoginResponse;

public interface IamService {

  LoginResponse login(LoginRequest loginRequest);

  LogoutResponse logout(LogoutRequest logoutRequest);
}
