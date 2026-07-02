
package com.sdewa.orderapi.lib.jwt;

import org.springframework.stereotype.Service;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.sdewa.orderapi.lib.jwt.JWTClaims;

@Service
public class JWTService {
    
    public String generateToken(String secureKey, long expirationTime, JWTClaims claims) {
        // Implement token generation logic here
        
        return Jwts.builder()
            .setSubject(claims.getId())
            .addClaims(JWTClaims.toClaim(claims))
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(Keys.hmacShaKeyFor(secureKey.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }
}