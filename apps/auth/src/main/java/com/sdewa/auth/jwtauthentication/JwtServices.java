package com.sdewa.auth.jwtauthentication;

import java.util.Optional;
import java.util.function.Predicate;

import io.jsonwebtoken.Claims;

public interface JwtServices {
    String generateToken(String secret, long expiration, JwtClaim claim);

    JwtClaim parseToken(String secret, String token);

    Optional<JwtClaim> parseToken(String secret, String token, Predicate<Claims> claimPredicate);

}