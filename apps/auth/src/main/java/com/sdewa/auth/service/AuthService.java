package com.sdewa.auth.service;

import org.springframework.http.ResponseEntity;

import com.sdewa.auth.dto.AuthResponseDto;
import com.sdewa.auth.dto.LoginRequestDto;
import com.sdewa.auth.dto.RefreshTokenRequestDto;
import com.sdewa.auth.dto.SingupRequestDto;

public interface AuthService {
    ResponseEntity<AuthResponseDto> signup(SingupRequestDto signupRequestDto);

    ResponseEntity<AuthResponseDto> login(LoginRequestDto loginRequestDto);

    ResponseEntity<AuthResponseDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}