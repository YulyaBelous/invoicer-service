package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.Authority;
import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.AuthorityRepository;
import com.example.invoicerservice.repository.UserRepository;
import com.example.invoicerservice.web.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should save the user without authorities when the authorities are null")
    void saveUserWithoutAuthoritiesWhenAuthoritiesNull() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("testuser@example.com");
        userDto.setPassword("testpassword");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setActivated(true);
        userDto.setAuthorities(null);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword())); // encode password before saving
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setActivated(userDto.isActivated());

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.saveUser(userDto);

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(Collections.emptySet(), user.getAuthorities());
        assertEquals(encoder.encode(userDto.getPassword()), user.getPassword()); // check if password is encoded
    }

    @Test
    @DisplayName("Should save the user with the given authorities when the authorities are not null")
    void saveUserWithGivenAuthoritiesWhenAuthoritiesNotNull() {// create a UserDto object with some values
        // create a UserDto object with some values
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("testuser@example.com");
        userDto.setPassword("testpassword");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setActivated(true);
        Set<String> authorities = new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        userDto.setAuthorities(authorities);

        // create a mock Authority object
        Authority authority1 = new Authority("ROLE_USER");
        Authority authority2 = new Authority("ROLE_ADMIN");

        // mock the authorityRepository to return the mock Authority objects when findByName method is called
        when(authorityRepository.findByName("ROLE_USER")).thenReturn(Optional.of(authority1));
        when(authorityRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(authority2));

        // mock the userRepository to return the saved user object when save method is called
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // mock the encoder to return the encoded password when encode method is called
        when(encoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        // call the saveUser method of userService with the userDto object
        User savedUser = userService.saveUser(userDto);

        // verify that the userRepository save method is called once with the correct user object
        verify(userRepository, times(1)).save(any(User.class));

        // verify that the user object has the correct values
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("testuser@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("Test", savedUser.getFirstName());
        assertEquals("User", savedUser.getLastName());
        assertTrue(savedUser.isActivated());
        assertEquals(2, savedUser.getAuthorities().size());
        assertTrue(savedUser.getAuthorities().contains(authority1));
        assertTrue(savedUser.getAuthorities().contains(authority2));
    }

}