package com.example.invoicerservice.security;

import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    static final String NON_EXISTING_USERNAME = "nonExistingUser";
    static final String USERNAME = "testUser";
    static final String PASSWORD = "testPassword";
    static final String EMAIL = "testuser@example.com";
    static final String FIRST_NAME = "Test";
    static final String LAST_NAME = "User";

    @Test
    @DisplayName("Should throw UsernameNotFoundException when the user does not exist")
    void loadUserByUsernameWhenUserDoesNotExistThenThrowException() {

        // Set up the mock userRepository to return an empty Optional when findByUsername method is called with NON_EXISTING_USERNAME
        when(userRepository.findByUsername(NON_EXISTING_USERNAME)).thenReturn(Optional.empty());

        // Call the loadUserByUsername method of customUserDetailsService with NON_EXISTING_USERNAME and verify that it throws UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(NON_EXISTING_USERNAME);
        });

        // Verify that the userRepository findByUsername method is called once with NON_EXISTING_USERNAME
        verify(userRepository, times(1)).findByUsername(NON_EXISTING_USERNAME);
    }

    @Test
    @DisplayName("Should load user by username when the user exists")
    void loadUserByUsernameWhenUserExists() {

        // Create a User object with the test user's details
        User user = new User(USERNAME, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, true);

        // Set up the mock userRepository to return the user object when findByUsername method is called with USERNAME
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        // Call the loadUserByUsername method of customUserDetailsService with USERNAME
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(USERNAME);

        // Verify that the user details object has the correct values
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertEquals(user.getAuthorities().size(), userDetails.getAuthorities().size());

        // Verify that the userRepository findByUsername method is called once with any string
        verify(userRepository, times(1)).findByUsername(anyString());
    }

}