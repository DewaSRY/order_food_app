package com.sdewa.auth.jwtauthentication;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.function.Predicate;

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
    public JwtClaim parseToken(String secret, String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return JwtClaim.fromMap(claims);
    }

    @Override
    public Optional<JwtClaim> parseToken(String secret, String token, Predicate<Claims> claimPredicate) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            if (claimPredicate.test(claims)) {
                return Optional.empty();
            }

            return Optional.of(JwtClaim.fromMap(claims));

        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
