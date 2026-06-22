package com.sdewa.auth.jwtauthentication;

import java.util.Map;

import lombok.Data;

@Data
public class JwtClaim {
    public String userid;
    public String email;

    public static Map<String, Object> toMap(JwtClaim claim) {
        return Map.of(
                "userid", claim.getUserid(),
                "email", claim.getEmail());
    }

    public static JwtClaim fromMap(Map<String, Object> map) {
        JwtClaim claim = new JwtClaim();
        claim.setUserid((String) map.get("userid"));
        claim.setEmail((String) map.get("email"));
        return claim;
    }
}