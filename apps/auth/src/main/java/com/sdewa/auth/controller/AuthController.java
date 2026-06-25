package com.sdewa.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdewa.auth.dto.AuthResponseDto;
import com.sdewa.auth.dto.LoginRequestDto;
import com.sdewa.auth.dto.RefreshTokenRequestDto;
import com.sdewa.auth.dto.SingupRequestDto;
import com.sdewa.auth.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> useSignup(@RequestBody SingupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> userLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}
