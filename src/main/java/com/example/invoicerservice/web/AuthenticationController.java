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

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        userService.saveUser(userDto);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), authorities, userDetails.getFirstName(), userDetails.getLastName(), userDetails.isActivated()));
    }
}
