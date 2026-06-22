package com.sdewa.auth.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

@Getter
public class AppConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refreshExpiration}")
    private long jwtRefreshExpiration;
}