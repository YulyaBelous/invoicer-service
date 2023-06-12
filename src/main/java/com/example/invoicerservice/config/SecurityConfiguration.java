package com.example.invoicerservice.config;

import com.example.invoicerservice.security.jwt.AuthEntryPointJwt;
import com.example.invoicerservice.security.jwt.AuthTokenFilter;
import com.example.invoicerservice.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * The SecurityConfiguration class is a Spring configuration class that provides configuration for the application's
 * security settings. It configures the authentication and authorization mechanisms used by the application, as well
 * as the CORS settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthEntryPointJwt unauthorizedHandler;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(AuthEntryPointJwt unauthorizedHandler, CustomUserDetailsService userDetailsService,
                                 PasswordEncoder passwordEncoder) {

        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configures the security filter chain used by the application. This method sets up the CORS settings, disables
     * CSRF protection, configures the authentication and authorization mechanisms, and adds the authentication token
     * filter to the filter chain.
     *
     * @param http the HttpSecurity object to configure
     * @return the SecurityFilterChain object
     * @throws Exception if an error occurs while configuring the filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Returns a new instance of the AuthTokenFilter class, which is a filter that intercepts requests and validates
     * authentication tokens.
     *
     * @return a new instance of the AuthTokenFilter class
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Returns a new instance of the DaoAuthenticationProvider class, which is an authentication provider that uses
     * a user details service and a password encoder to authenticate users.
     *
     * @return a new instance of the DaoAuthenticationProvider class
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    /**
     * Returns a new instance of the AuthenticationManager class, which is used to authenticate users.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return a new instance of the AuthenticationManager class
     * @throws Exception if an error occurs while creating the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Returns a new instance of the CorsConfigurationSource class, which is used to configure the CORS settings for
     * the application.
     *
     * @return a new instance of the CorsConfigurationSource class
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

