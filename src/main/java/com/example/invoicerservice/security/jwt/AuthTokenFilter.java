package com.example.invoicerservice.security.jwt;

import com.example.invoicerservice.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The AuthTokenFilter class is responsible for intercepting incoming HTTP requests and extracting the JWT token from
 * the Authorization header.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Intercepts incoming HTTP requests and extracts the JWT token from the Authorization header. If the token is
     * valid, it sets the user authentication in the SecurityContext and allows the request to proceed. If the token
     * is invalid, it logs an error and allows the request to proceed without authentication.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            // Extract the JWT token from the Authorization header of the HTTP servlet request
            String jwt = parseJwt(request);

            // If the token is not null and is valid, set the user authentication in the SecurityContext
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // Extract the username from the JWT token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Load the user details from the database using the username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create an authentication object with the user details and set it in the SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {

            logger.error("Cannot set user authentication: {}", e);
        }

        // Allow the request to proceed down the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP servlet request.
     *
     * @param request the HTTP servlet request
     * @return the JWT token, or null if the Authorization header is missing or does not start with "Bearer "
     */
    private String parseJwt(HttpServletRequest request) {

        // Get the value of the Authorization header from the HTTP servlet request
        String headerAuth = request.getHeader("Authorization");

        // If the Authorization header is not null and starts with "Bearer ", extract the JWT token
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {

            // Return the JWT token without the "Bearer " prefix
            return headerAuth.substring(7, headerAuth.length());
        }

        // Return null if the Authorization header is missing or does not start
        return null;
    }
}
