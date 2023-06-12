package com.example.invoicerservice.web;

import com.example.invoicerservice.repository.UserRepository;
import com.example.invoicerservice.web.response.JwtResponse;
import com.example.invoicerservice.web.response.MessageResponse;
import com.example.invoicerservice.security.jwt.JwtUtils;
import com.example.invoicerservice.security.CustomUserDetails;
import com.example.invoicerservice.service.UserService;
import com.example.invoicerservice.web.dto.LoginDto;
import com.example.invoicerservice.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The AuthenticationController class is a Spring REST controller that provides endpoints for user authentication.
 * It handles HTTP requests and responses for the /user/registration and /user/login endpoints, which allow users to
 * register and log in to the application.
 */
@RestController
@RequestMapping("/user")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(UserRepository userRepository, AuthenticationManager authenticationManager,
                                    UserService userService, JwtUtils jwtUtils) {

        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Registers a new user with the specified details. If the username or email is already taken, a bad request
     * response is returned. Otherwise, the user is saved and a success response is returned.
     *
     * @param userDto the user details
     * @return a response entity with a success or error message
     */
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {

        // Check if the username is already taken
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        // Check if the email is already in use
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        userService.saveUser(userDto);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Logs in a user with the specified username and password. If the credentials are invalid, an unauthorized
     * response is returned. Otherwise, a JWT token is generated and returned along with the user details and
     * authorities.
     *
     * @param loginDto the login details
     * @return a response entity with a JWT token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {

        // Authenticate the user with the specified username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        // Set the authentication object in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token for the authenticated user
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get the user details from the authentication object
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Get the authorities of the user and convert them to a list of strings
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Create a new JwtResponse object with the JWT token and user details
        JwtResponse response = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
                authorities, userDetails.getFirstName(), userDetails.getLastName(), userDetails.isActivated());

        // Return a response entity with the JwtResponse object
        return ResponseEntity.ok(response);
    }
}
