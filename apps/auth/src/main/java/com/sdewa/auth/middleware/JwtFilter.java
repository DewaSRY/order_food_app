package com.sdewa.auth.middleware;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sdewa.auth.utils.AppConfig;
import com.sdewa.auth.jwtauthentication.JwtClaim;
import com.sdewa.auth.jwtauthentication.JwtServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final AppConfig appConfig;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.startsWith("/api/auth/login") || path.startsWith("/api/auth/signup");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        Optional<JwtClaim> claim = jwtServices.parseToken(appConfig.getJwtSecret(), token, c -> true);

        if (!claim.isPresent()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                claim.get().getUserid(), null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
