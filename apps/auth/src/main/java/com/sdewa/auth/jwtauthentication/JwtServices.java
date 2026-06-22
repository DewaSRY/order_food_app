package com.sdewa.auth.jwtauthentication;

public interface JwtServices {
    String generateToken(String secreet, long expiration, JwtClaim claim);

    boolean validateToken(String secret, String token);
}