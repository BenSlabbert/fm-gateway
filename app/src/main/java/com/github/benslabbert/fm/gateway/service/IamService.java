package com.github.benslabbert.fm.gateway.service;

import com.github.benslabbert.fm.gateway.dto.v1.LoginRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutRequestDto;
import com.github.benslabbert.fm.gateway.dto.v1.LogoutResponseDto;
import com.github.benslabbert.fm.iam.proto.service.v1.LoginResponse;

public interface IamService {

  LoginResponse login(LoginRequestDto loginRequestDto);

  LogoutResponseDto logout(LogoutRequestDto logoutRequestDto);
}
