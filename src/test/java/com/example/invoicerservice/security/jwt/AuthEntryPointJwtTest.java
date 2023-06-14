package com.example.invoicerservice.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthEntryPointJwtTest {

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    @Test
    @DisplayName("Should send an unauthorized error when authentication exception occurs")
    void commenceWhenAuthenticationExceptionOccursThenSendUnauthorizedError() {

        // Create mock objects for request, response, and authentication exception
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        try {

            // Call the commence method of AuthEntryPointJwt with the mock objects
            authEntryPointJwt.commence(request, response, authException);

            // Verify that the response object sends an unauthorized error with the correct message
            verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }

    }

}