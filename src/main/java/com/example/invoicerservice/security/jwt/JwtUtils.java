package com.example.invoicerservice.security.jwt;

import com.example.invoicerservice.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import io.jsonwebtoken.*;

/**
 * The JwtUtils class is responsible for generating and validating JSON Web Tokens (JWTs) in the application. It
 * contains methods for generating a JWT token from a user's authentication details, validating a JWT token, and
 * extracting the username from a JWT token.
 */
@Component
public class JwtUtils {

    @Value("${example.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${example.app.jwtSecret}")
    private String jwtSecret;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * Validates a JWT token by parsing its claims and verifying its signature.
     *
     * @param authToken the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {

        try {
            // Parse the JWT token and verify its signature using the secret key
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

            // Return true if the token is valid
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        // Return false if the token is invalid
        return false;
    }

    /**
     * Generates a JWT token from a user's authentication details.
     *
     * @param authentication the user's authentication details
     * @return the JWT token
     */
    public String generateJwtToken(Authentication authentication) {

        // Get the user details from the authentication object
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        // Build the JWT token with the user's username, current date and the expiration date
        return Jwts.builder().setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUserNameFromJwtToken(String token) {

        // Parse the JWT token and extract the subject (username) from its claims
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

}
