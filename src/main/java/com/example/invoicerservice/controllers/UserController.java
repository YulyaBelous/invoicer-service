package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Authority;
import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.IAuthorityRepository;
import com.example.invoicerservice.repository.IUserRepository;
import com.example.invoicerservice.response.JwtResponse;
import com.example.invoicerservice.response.MessageResponse;
import com.example.invoicerservice.security.jwt.JwtUtils;
import com.example.invoicerservice.security.services.UserDetailsImpl;
import com.example.invoicerservice.services.dto.LoginDto;
import com.example.invoicerservice.services.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IAuthorityRepository authorityRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(userDto.getUsername(), userDto.getEmail(),
                encoder.encode(userDto.getPassword()), userDto.getFirstName(), userDto.getLastName());

        Set<String> strRoles = userDto.getAuthorities();
        Set<Authority> roles = new HashSet<>();

        if(strRoles == null) {
            Authority userRole = authorityRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            Authority userRole = authorityRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }

        user.setAuthorities(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("user registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), authorities, userDetails.getFirstName(), userDetails.getLastName()));
    }

}
