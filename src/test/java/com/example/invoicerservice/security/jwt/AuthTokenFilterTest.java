package com.example.invoicerservice.security.jwt;

import com.example.invoicerservice.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    static final String AUTH_TOKEN = "jwt";
    static final String USERNAME = "testUser";
    static final String HEADER = "Authorization";

    @Test
    @DisplayName("Should not set user authentication when JWT is missing")
    void doFilterInternalWhenJwtIsMissing() {
        try {

            // Call the doFilterInternal method of AuthTokenFilter with mock objects for request, response, and filterChain
            authTokenFilter.doFilterInternal(request, response, filterChain);

            // Verify that the authentication object in the SecurityContextHolder is null
            assertNull(SecurityContextHolder.getContext().getAuthentication());

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should not set user authentication when JWT is invalid")
    void doFilterInternalWhenJwtIsInvalid() {
        try {

            // Set up mock objects for request, response, and userDetails
            when(request.getHeader(HEADER)).thenReturn("Bearer " + AUTH_TOKEN);
            when(jwtUtils.validateJwtToken(AUTH_TOKEN)).thenReturn(false);

            // Call the doFilterInternal method of AuthTokenFilter with the mock objects
            authTokenFilter.doFilterInternal(request, response, filterChain);

            // Verify that the filterChain object's doFilter method is called once with the request and response objects
            verify(filterChain, times(1)).doFilter(request, response);
            // Verify that the authentication object in the SecurityContextHolder is null
            assertNull(SecurityContextHolder.getContext().getAuthentication());

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should continue with the filter chain after processing")
    void doFilterInternalContinuesWithFilterChain() {
        try {

            // Set up mock objects for request, response, and userDetails
            when(request.getHeader(HEADER)).thenReturn("Bearer " + AUTH_TOKEN);
            when(jwtUtils.validateJwtToken(AUTH_TOKEN)).thenReturn(true);
            when(jwtUtils.getUserNameFromJwtToken(AUTH_TOKEN)).thenReturn(USERNAME);
            when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);

            // Call the doFilterInternal method of AuthTokenFilter with the mock objects
            authTokenFilter.doFilterInternal(request, response, filterChain);

            // Verify that the filterChain object's doFilter method is called once with the request and response objects
            verify(filterChain, times(1)).doFilter(request, response);

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should set user authentication when JWT is valid")
    void doFilterInternalWhenJwtIsValid() {
        try {

            // Set up mock objects for request, response, and userDetails
            when(request.getHeader(HEADER)).thenReturn("Bearer " + AUTH_TOKEN);
            when(jwtUtils.validateJwtToken(AUTH_TOKEN)).thenReturn(true);
            when(jwtUtils.getUserNameFromJwtToken(AUTH_TOKEN)).thenReturn(USERNAME);
            when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);

            // Call the doFilterInternal method of AuthTokenFilter with the mock objects
            authTokenFilter.doFilterInternal(request, response, filterChain);

            // Verify
            verify(userDetailsService, times(1)).loadUserByUsername(USERNAME);
            verify(filterChain, times(1)).doFilter(request, response);
            assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}