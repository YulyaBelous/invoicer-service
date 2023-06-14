package com.example.invoicerservice.security;

import com.example.invoicerservice.domain.Authority;
import com.example.invoicerservice.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomUserDetailsTest {

    static final Long ID = 1L;
    static final String USERNAME = "testUser";
    static final String PASSWORD = "testPassword";
    static final String EMAIL = "testuser@example.com";
    static final String FIRST_NAME = "Test";
    static final String LAST_NAME = "User";
    static final String ROLE_USER = "ROLE_USER";

    @Test
    @DisplayName("Should build CustomUserDetails with correct properties from User object")
    void buildCustomUserDetailsFromUser() {

        // Create a mock User object
        User user = mock(User.class);
        when(user.getId()).thenReturn(ID);
        when(user.getUsername()).thenReturn(USERNAME);
        when(user.getEmail()).thenReturn(EMAIL);
        when(user.getPassword()).thenReturn(PASSWORD);
        when(user.getFirstName()).thenReturn(FIRST_NAME);
        when(user.getLastName()).thenReturn(LAST_NAME);
        when(user.isActivated()).thenReturn(true);

        // Create a mock Authority object
        Authority authority = mock(Authority.class);
        when(authority.getName()).thenReturn(ROLE_USER);

        // Add the authority to the user's authorities set
        Set<Authority> authorities = Set.of(authority);
        when(user.getAuthorities()).thenReturn(authorities);

        // Call the build method and assert that the CustomUserDetails object is built correctly
        CustomUserDetails customUserDetails = CustomUserDetails.build(user);
        assertEquals(ID, customUserDetails.getId());
        assertEquals(USERNAME, customUserDetails.getUsername());
        assertEquals(EMAIL, customUserDetails.getEmail());
        assertEquals(PASSWORD, customUserDetails.getPassword());
        assertEquals(FIRST_NAME, customUserDetails.getFirstName());
        assertEquals(LAST_NAME, customUserDetails.getLastName());
        assertTrue(customUserDetails.isActivated());
        assertEquals(1, customUserDetails.getAuthorities().size());
        assertEquals(ROLE_USER, customUserDetails.getAuthorities().iterator().next().getAuthority());
    }

}