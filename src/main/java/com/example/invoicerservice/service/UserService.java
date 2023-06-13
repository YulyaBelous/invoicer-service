package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.Authority;
import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.AuthorityRepository;
import com.example.invoicerservice.repository.UserRepository;
import com.example.invoicerservice.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The UserService class is responsible for managing user data in the application. It provides methods for creating,
 * updating, and retrieving user data from the database.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.encoder = encoder;
    }

    /**
     * Saves a new user to the database with the specified user data.
     *
     * @param userDto the UserDto object containing the user data to save
     */
    public User saveUser(UserDto userDto) {

        // Create a new User object with the specified user data
        User user = new User(userDto.getUsername(), userDto.getEmail(),
                encoder.encode(userDto.getPassword()), userDto.getFirstName(), userDto.getLastName(), userDto.isActivated());

        // Set the user's authorities if they are specified
        if (userDto.getAuthorities() != null) {
            Set<Authority> authorities = userDto
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }

        // Save the user to the database
        userRepository.save(user);

        return user;
    }

    /**
     * Updates an existing user in the database with the specified user data.
     *
     * @param userDTO the UserDto object containing the updated user data
     */
    public User updateUser(UserDto userDTO) {

        // Find the user with the specified ID
        Optional<User> userOptional = Optional
                .of(userRepository.findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get);

        // Update the user's data if it exists
        userOptional.ifPresent(user -> {
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setActivated(userDTO.isActivated());
            if (userDTO.getEmail() != null) {
                user.setEmail(userDTO.getEmail());
            }
            Set<Authority> managedAuthorities = user.getAuthorities();
            managedAuthorities.clear();
            userDTO
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
        });

        // Save the updated user to the database
        userRepository.save(userOptional.get());

        return userOptional.get();
    }

}
