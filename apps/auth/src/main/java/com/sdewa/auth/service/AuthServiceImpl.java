package com.sdewa.auth.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sdewa.auth.dto.AuthResponseDto;
import com.sdewa.auth.dto.LoginRequestDto;
import com.sdewa.auth.dto.RefreshTokenRequestDto;
import com.sdewa.auth.dto.SingupRequestDto;
import com.sdewa.auth.entity.UserEntity;
import com.sdewa.auth.jwtauthentication.JwtClaim;
import com.sdewa.auth.jwtauthentication.JwtServices;
import com.sdewa.auth.repo.UserRepo;
import com.sdewa.auth.utils.AppConfig;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final JwtServices jwtServices;
    private final PasswordEncoder passwordEncoder;
    private final AppConfig appConfig;

    @Override
    public ResponseEntity<AuthResponseDto> signup(SingupRequestDto signupRequestDto) {
        Optional<UserEntity> existingUser = userRepo.findByEmail(signupRequestDto.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (!signupRequestDto.getPassword().equals(signupRequestDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(null);
        }

        UserEntity newUser = UserEntity.builder()
                .username(signupRequestDto.getUsername())
                .email(signupRequestDto.getEmail())
                .hashedPassword(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build();
        UserEntity savedUser = userRepo.save(newUser);

        AuthResponseDto authResponse = generateTokens(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @Override
    public ResponseEntity<AuthResponseDto> login(LoginRequestDto loginRequestDto) {
        Optional<UserEntity> userOptional = userRepo.findByEmail(loginRequestDto.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserEntity user = userOptional.get();

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getHashedPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        AuthResponseDto authResponse = generateTokens(user);
        return ResponseEntity.ok(authResponse);
    }

    @Override
    public ResponseEntity<AuthResponseDto> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        Optional<JwtClaim> claim = jwtServices.parseToken(appConfig.getJwtSecret(),
                refreshTokenRequestDto.getRefreshToken(), c -> true);

        if (claim.isEmpty()) {
            return ResponseEntity.status(401).body(null);
        }

        String accessToken = jwtServices.generateToken(
                appConfig.getJwtSecret(),
                appConfig.getJwtExpiration(),
                JwtClaim.builder()
                        .userid(claim.get().getUserid())
                        .email(claim.get().getEmail())
                        .build());

        AuthResponseDto newAuthResponse = AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenRequestDto.getRefreshToken())
                .build();

        return ResponseEntity.ok(newAuthResponse);

    }

    private AuthResponseDto generateTokens(UserEntity user) {
        String accessToken = jwtServices.generateToken(
                appConfig.getJwtSecret(),
                appConfig.getJwtExpiration(),
                JwtClaim.builder()
                        .userid(user.getId().toString())
                        .email(user.getEmail())
                        .build());

        String refreshToken = jwtServices.generateToken(
                appConfig.getJwtSecret(),
                appConfig.getJwtRefreshExpiration(),
                JwtClaim.builder()
                        .userid(user.getId().toString())
                        .email(user.getEmail())
                        .build());

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
