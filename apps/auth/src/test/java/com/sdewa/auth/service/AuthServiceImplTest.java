package com.sdewa.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sdewa.auth.dto.AuthResponseDto;
import com.sdewa.auth.dto.LoginRequestDto;
import com.sdewa.auth.dto.RefreshTokenRequestDto;
import com.sdewa.auth.dto.SingupRequestDto;
import com.sdewa.auth.entity.UserEntity;
import com.sdewa.auth.jwtauthentication.JwtClaim;
import com.sdewa.auth.jwtauthentication.JwtServices;
import com.sdewa.auth.repo.UserRepo;
import com.sdewa.auth.utils.AppConfig;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtServices jwtServices;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private AuthServiceImpl authService;

    private String testSecret;
    private long testExpiration;
    private long testRefreshExpiration;

    @BeforeEach
    public void setUp() {
        testSecret = "testSecret123";
        testExpiration = 3600000L; // 1 hour
        testRefreshExpiration = 86400000L; // 24 hours

        lenient().when(appConfig.getJwtSecret()).thenReturn(testSecret);
        lenient().when(appConfig.getJwtExpiration()).thenReturn(testExpiration);
        lenient().when(appConfig.getJwtRefreshExpiration()).thenReturn(testRefreshExpiration);
    }

    // Signup Tests

    @Test
    public void testSignup_Success_ReturnsTokens() {
        // Arrange
        SingupRequestDto signupRequest = SingupRequestDto.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        when(userRepo.findByEmail(signupRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("hashedPassword");

        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .hashedPassword("hashedPassword")
                .build();

        when(userRepo.save(any(UserEntity.class))).thenReturn(savedUser);
        when(jwtServices.generateToken(anyString(), anyLong(), any(JwtClaim.class)))
                .thenReturn("accessToken")
                .thenReturn("refreshToken");

        // Act
        ResponseEntity<AuthResponseDto> response = authService.signup(signupRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());

        verify(userRepo).findByEmail(signupRequest.getEmail());
        verify(passwordEncoder).encode(signupRequest.getPassword());
        verify(userRepo).save(any(UserEntity.class));
        verify(jwtServices, times(2)).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    @Test
    public void testSignup_UserAlreadyExists_ReturnsBadRequest() {
        // Arrange
        SingupRequestDto signupRequest = SingupRequestDto.builder()
                .username("testuser")
                .email("existing@example.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        UserEntity existingUser = UserEntity.builder()
                .id(1L)
                .username("existinguser")
                .email("existing@example.com")
                .hashedPassword("hashedPassword")
                .build();

        when(userRepo.findByEmail(signupRequest.getEmail())).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<AuthResponseDto> response = authService.signup(signupRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(userRepo).findByEmail(signupRequest.getEmail());
        verify(userRepo, never()).save(any(UserEntity.class));
        verify(jwtServices, never()).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    @Test
    public void testSignup_PasswordMismatch_ReturnsBadRequest() {
        // Arrange
        SingupRequestDto signupRequest = SingupRequestDto.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .confirmPassword("differentPassword")
                .build();

        when(userRepo.findByEmail(signupRequest.getEmail())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthResponseDto> response = authService.signup(signupRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(userRepo).findByEmail(signupRequest.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepo, never()).save(any(UserEntity.class));
        verify(jwtServices, never()).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    // Login Tests

    @Test
    public void testLogin_Success_ReturnsTokens() {
        // Arrange
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .hashedPassword("hashedPassword")
                .build();

        when(userRepo.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getHashedPassword())).thenReturn(true);
        when(jwtServices.generateToken(anyString(), anyLong(), any(JwtClaim.class)))
                .thenReturn("accessToken")
                .thenReturn("refreshToken");

        // Act
        ResponseEntity<AuthResponseDto> response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());

        verify(userRepo).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), user.getHashedPassword());
        verify(jwtServices, times(2)).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    @Test
    public void testLogin_UserNotFound_ReturnsUnauthorized() {
        // Arrange
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("nonexistent@example.com")
                .password("password123")
                .build();

        when(userRepo.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthResponseDto> response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());

        verify(userRepo).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtServices, never()).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    @Test
    public void testLogin_WrongPassword_ReturnsUnauthorized() {
        // Arrange
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("test@example.com")
                .password("wrongPassword")
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .hashedPassword("hashedPassword")
                .build();

        when(userRepo.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getHashedPassword())).thenReturn(false);

        // Act
        ResponseEntity<AuthResponseDto> response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());

        verify(userRepo).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), user.getHashedPassword());
        verify(jwtServices, never()).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    // Refresh Token Tests

    @Test
    public void testRefreshToken_Success_ReturnsNewAccessToken() {
        // Arrange
        RefreshTokenRequestDto refreshRequest = RefreshTokenRequestDto.builder()
                .refreshToken("validRefreshToken")
                .build();

        JwtClaim claim = JwtClaim.builder()
                .userid("1")
                .email("test@example.com")
                .build();

        when(jwtServices.parseToken(eq(testSecret), eq(refreshRequest.getRefreshToken()), any()))
                .thenReturn(Optional.of(claim));
        when(jwtServices.generateToken(eq(testSecret), eq(testExpiration), any(JwtClaim.class)))
                .thenReturn("newAccessToken");

        // Act
        ResponseEntity<AuthResponseDto> response = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newAccessToken", response.getBody().getAccessToken());
        assertEquals("validRefreshToken", response.getBody().getRefreshToken());

        verify(jwtServices).parseToken(eq(testSecret), eq(refreshRequest.getRefreshToken()), any());
        verify(jwtServices).generateToken(eq(testSecret), eq(testExpiration), any(JwtClaim.class));
    }

    @Test
    public void testRefreshToken_InvalidToken_ReturnsUnauthorized() {
        // Arrange
        RefreshTokenRequestDto refreshRequest = RefreshTokenRequestDto.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        when(jwtServices.parseToken(eq(testSecret), eq(refreshRequest.getRefreshToken()), any()))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthResponseDto> response = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());

        verify(jwtServices).parseToken(eq(testSecret), eq(refreshRequest.getRefreshToken()), any());
        verify(jwtServices, never()).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }

    @Test
    public void testRefreshToken_ExpiredToken_ReturnsUnauthorized() {
        // Arrange
        RefreshTokenRequestDto refreshRequest = RefreshTokenRequestDto.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        // When token is expired, parseToken with predicate returns empty
        when(jwtServices.parseToken(eq(testSecret), eq(refreshRequest.getRefreshToken()), any()))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthResponseDto> response = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());

        verify(jwtServices).parseToken(eq(testSecret), eq(refreshRequest.getRefreshToken()), any());
        verify(jwtServices, never()).generateToken(anyString(), anyLong(), any(JwtClaim.class));
    }
}
