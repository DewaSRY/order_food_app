package com.sdewa.auth.jwtauthentication;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServicesImpl implements JwtServices {

    @Override
    public String generateToken(String secreet, long expiration, JwtClaim claim) {
        return Jwts.builder()
                .subject(claim.getUserid())
                .claims(JwtClaim.toMap(claim))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secreet)))
                .compact();
    }

    @Override
    public boolean validateToken(String secret, String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

}
