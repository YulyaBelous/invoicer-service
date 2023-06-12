package com.example.invoicerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The PasswordEncoderConfiguration class is a Spring configuration class that provides a bean for the password encoder.
 * The password encoder is used to encode and decode passwords for user authentication.
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * Returns a new instance of the BCryptPasswordEncoder class, which is a password encoder that uses the bcrypt
     * algorithm to encode and decode passwords.
     *
     * @return a new instance of the BCryptPasswordEncoder class
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
