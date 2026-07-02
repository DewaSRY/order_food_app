package com.sdewa.orderapi.lib.jwt;

import java.util.Map;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class JWTClaims {
    private String id;
    private String email;`


    public static Claims toClaim(JWTClaims claims) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("email", claims.getEmail());
        claimsMap.put("id", claims.getId());

        return Jwts.claims()
                .setSubject(claims.getId())
                .addClaims(claimsMap);
        
    }

    public static JWTClaims fromClaim(Claims claims) {
        return JWTClaims.builder()
                .id(claims.getSubject())
                .email((String) claims.get("email"))
                .build();
    }
}
