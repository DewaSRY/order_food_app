package com.sdewa.auth.jwtauthentication;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtServicesTest {

    private JwtServicesImpl jwtServices;
    private String testSecret;
    private long testExpiration;

    @BeforeEach
    public void setUp() {
        jwtServices = new JwtServicesImpl();
        testSecret = Base64.getEncoder()
                .encodeToString("ThisIsAVerySecureSecretKeyForTestingPurposesOnly12345".getBytes());
        testExpiration = 3600000; // 1 hour
    }

    @Test
    public void testGenerateToken_Success() {
        JwtClaim claim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();

        // Act
        String token = jwtServices.generateToken(testSecret, testExpiration, claim);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts separated by dots
    }

    @Test
    public void testValidateToken_ValidToken_ReturnsTrue() {
        // Arrange
        JwtClaim claim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();
        String token = jwtServices.generateToken(testSecret, testExpiration, claim);

        // Act
        Optional<JwtClaim> isValid = jwtServices.parseToken(testSecret, token, c -> true);

        // Assert
        assertTrue(isValid.isPresent());
    }

    @Test
    public void testValidateToken_ExpiredToken_ReturnsFalse() throws InterruptedException {
        // Arrange
        JwtClaim claim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();
        long shortExpiration = 1; // 1 millisecond
        String token = jwtServices.generateToken(testSecret, shortExpiration, claim);

        // Wait for token to expire
        Thread.sleep(10);

        // Act
        Optional<JwtClaim> isValid = jwtServices.parseToken(testSecret, token, c -> true);

        // Assert
        assertFalse(isValid.isPresent());
    }

    @Test
    public void testParseToken_ValidToken_ReturnsCorrectClaims() {
        JwtClaim originalClaim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();
        String token = jwtServices.generateToken(testSecret, testExpiration, originalClaim);

        // Act
        JwtClaim parsedClaim = jwtServices.parseToken(testSecret, token);

        // Assert
        assertNotNull(parsedClaim);
        assertEquals(originalClaim.getUserid(), parsedClaim.getUserid());
        assertEquals(originalClaim.getEmail(), parsedClaim.getEmail());
    }

    @Test
    public void testParseTokenWithPredicate_ValidTokenAndPredicateTrue_ReturnsOptionalWithClaim() {
        JwtClaim originalClaim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();

        String token = jwtServices.generateToken(testSecret, testExpiration, originalClaim);
        Optional<JwtClaim> result = jwtServices.parseToken(testSecret, token, c -> true);

        assertTrue(result.isPresent());
        assertEquals(originalClaim.getUserid(), result.get().getUserid());
        assertEquals(originalClaim.getEmail(), result.get().getEmail());
    }

    @Test
    public void testParseTokenWithPredicate_ValidTokenAndPredicateFalse_ReturnsEmptyOptional() {
        JwtClaim originalClaim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();
        String token = jwtServices.generateToken(testSecret, testExpiration, originalClaim);

        Optional<JwtClaim> result = jwtServices.parseToken(testSecret, token, c -> false);

        assertFalse(result.isPresent());
    }

    @Test
    public void testParseTokenWithPredicate_ValidTokenWithCustomPredicate_FiltersCorrectly() {
        JwtClaim originalClaim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();
        String token = jwtServices.generateToken(testSecret, testExpiration, originalClaim);

        Optional<JwtClaim> result = jwtServices.parseToken(
                testSecret,
                token,
                claim -> true);

        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getUserid());
    }

    @Test
    public void testParseTokenWithPredicate_InvalidToken_ReturnsEmptyOptional() {
        String invalidToken = "invalid.token.here";
        Optional<JwtClaim> result = jwtServices.parseToken(testSecret, invalidToken, claim -> true);
        assertFalse(result.isPresent());
    }

    @Test
    public void testParseTokenWithPredicate_ExpiredToken_ReturnsEmptyOptional() throws InterruptedException {
        JwtClaim originalClaim = JwtClaim.builder()
                .userid("user123")
                .email("test@example.com")
                .build();
        long shortExpiration = 1;
        String token = jwtServices.generateToken(testSecret, shortExpiration, originalClaim);

        Optional<JwtClaim> result = jwtServices.parseToken(testSecret, token, claim -> true);
        Thread.sleep(10);

        assertFalse(result.isPresent());
    }
}
