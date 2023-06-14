package com.example.invoicerservice.security.jwt;

import com.example.invoicerservice.security.CustomUserDetails;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    private CustomUserDetails userDetails;

    static final String INVALID_TOKEN = "invalidToken";
    static final String INVALID_SIGNATURE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    static final String UNSUPPORTED_TOKEN = "unsupportedToken";
    static final String USERNAME = "testUser";
    static final String PASSWORD = "password";
    static final String EMAIL = "testuser@example.com";
    static final String FIRST_NAME = "Test";
    static final String LAST_NAME = "User";
    static final Long ID = 1L;

    @BeforeEach
    void setUp() {

        // Set up a mock user details object for testing
        userDetails = new CustomUserDetails(ID, USERNAME, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, true, null);
    }

    @Test
    @DisplayName("Should throw an exception when the JWT token is invalid")
    void getUserNameFromJwtTokenWithInvalidTokenThenThrowException() {

        // Call the getUserNameFromJwtToken method of JwtUtils with the invalid token and verify that a MalformedJwtException is thrown
        assertThrows(MalformedJwtException.class, () -> jwtUtils.getUserNameFromJwtToken(INVALID_TOKEN));
    }

    @Test
    @DisplayName("Should return the correct username from a valid JWT token")
    void getUserNameFromJwtTokenWithValidToken() {

        // Create an authentication token with the mock user details
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null);

        // Generate a JWT token using the authentication token
        String token = jwtUtils.generateJwtToken(authenticationToken);

        // Call the getUserNameFromJwtToken method of JwtUtils with the generated token
        String username = jwtUtils.getUserNameFromJwtToken(token);

        //Verify that it returns the correct username
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    @DisplayName("Should generate a valid JWT token for a given authentication")
    void generateJwtTokenForGivenAuthentication() {

        // Create an authentication token with the mock user details
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // Generate JWT token
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        // Validate JWT token
        assertTrue(jwtUtils.validateJwtToken(jwtToken));

        // Extract username from JWT token
        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);

        // Verify
        assertEquals(username, userDetails.getUsername());
        assertFalse(jwtUtils.validateJwtToken(jwtToken + "invalid")); // invalid token should be false
    }

    @Test
    @DisplayName("Should return false when the JWT token is malformed")
    void validateJwtTokenWhenTokenIsMalformed() {

        // Call and Verify
        assertFalse(jwtUtils.validateJwtToken(INVALID_TOKEN));
    }

    @Test
    @DisplayName("Should return false when the JWT token is unsupported")
    void validateJwtTokenWhenTokenIsUnsupported() {

        // Call and Verify
        assertFalse(jwtUtils.validateJwtToken(UNSUPPORTED_TOKEN));
    }

    @Test
    @DisplayName("Should return true when the JWT token is valid")
    void validateJwtTokenWhenTokenIsValid() {

        // Create an authentication token with the mock user details
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // Generate JWT token
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        // Call the validateJwtToken method of JwtUtils with the generated token and verify that it returns true
        boolean result = jwtUtils.validateJwtToken(jwtToken);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when the JWT token has an invalid signature")
    void validateJwtTokenWhenTokenHasInvalidSignature() {

        // Call the validateJwtToken method of JwtUtils with the invalid token and verify that it returns false
        boolean result = jwtUtils.validateJwtToken(INVALID_SIGNATURE_TOKEN);
        assertFalse(result);
    }
}