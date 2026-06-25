package com.sdewa.auth.jwtauthentication;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtClaim {
    private String userid;
    private String email;

    public static Map<String, Object> toMap(JwtClaim claim) {
        return Map.of(
                "userid", claim.getUserid(),
                "email", claim.getEmail());
    }

    public static JwtClaim fromMap(Map<String, Object> map) {
        return JwtClaim.builder()
                .userid((String) map.get("userid"))
                .email((String) map.get("email"))
                .build();
    }
}