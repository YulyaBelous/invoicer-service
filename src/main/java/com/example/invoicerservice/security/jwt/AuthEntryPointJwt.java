package com.example.invoicerservice.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The AuthEntryPointJwt class is responsible for handling authentication errors in the application. It implements the
 * AuthenticationEntryPoint interface and overrides the commence method to send an HTTP 401 Unauthorized error response
 * to the client.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Sends an HTTP 401 Unauthorized error response to the client.
     *
     * @param request       the HTTP servlet request
     * @param response      the HTTP servlet response
     * @param authException the authentication exception that caused the error
     * @throws IOException if an I/O error occurs while sending the response
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Send an HTTP 401 Unauthorized error response to the client with the error message "Error: Unauthorized"
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }

}
