package com.sdewa.auth.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdewa.auth.dto.LoginRequestDto;
import com.sdewa.auth.dto.RefreshTokenRequestDto;
import com.sdewa.auth.dto.SingupRequestDto;
import com.sdewa.auth.jwtauthentication.JwtClaim;
import com.sdewa.auth.jwtauthentication.JwtServices;
import com.sdewa.auth.service.AuthService;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

        private final String BASE_URL = "/api/auth";

        @Autowired
        MockMvc mockMvc;

        ObjectMapper objectMapper = new ObjectMapper();

        @MockitoBean
        private AuthService authService;

        @MockitoBean
        private JwtServices jwtServices;

        @Test
        public void testSignup() throws Exception {
                SingupRequestDto pyload = SingupRequestDto.builder()
                                .username("testuser")
                                .email("testuser@example.com")
                                .password("password")
                                .confirmPassword("password")
                                .build();

                mockMvc.perform(post(BASE_URL + "/signup")
                                .content(objectMapper.writeValueAsString(pyload))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(authService).signup(any(SingupRequestDto.class));
        }

        @Test
        public void testLogin() throws Exception {
                LoginRequestDto pyload = LoginRequestDto.builder()
                                .email("testuser@example.com")
                                .password("password")
                                .build();

                mockMvc.perform(post(BASE_URL + "/login")
                                .content(objectMapper.writeValueAsString(pyload))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(authService).login(any(LoginRequestDto.class));
        }

        @Test
        public void testRefreshTokenWithoutBearerToken() throws Exception {
                RefreshTokenRequestDto pyload = RefreshTokenRequestDto.builder()
                                .refreshToken("refreshToken")
                                .build();

                mockMvc.perform(post(BASE_URL + "/refresh-token")
                                .content(objectMapper.writeValueAsString(pyload))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isForbidden());
        }

        @Test
        public void testRefreshTokenWithBearerToken() throws Exception {
                JwtClaim claim = JwtClaim.builder()
                                .userid("user123")
                                .email("testuser@example.com")
                                .build();

                when(jwtServices.parseToken(any(), any(), any())).thenReturn(Optional.of(claim));

                RefreshTokenRequestDto pyload = RefreshTokenRequestDto.builder()
                                .refreshToken("some-refresh-token")
                                .build();

                mockMvc.perform(post(BASE_URL + "/refresh-token")
                                .header("Authorization", "Bearer valid-access-token")
                                .content(objectMapper.writeValueAsString(pyload))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                verify(authService).refreshToken(any(RefreshTokenRequestDto.class));
        }

}
