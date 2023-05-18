package com.example.invoicerservice.security.services;

import com.example.invoicerservice.entities.Authority;
import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.IAuthorityRepository;
import com.example.invoicerservice.repository.IUserRepository;
import com.example.invoicerservice.response.MessageResponse;
import com.example.invoicerservice.services.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final IUserRepository userRepository;

    @Autowired
    private final IAuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserService(IUserRepository userRepository, IAuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public void saveUser(UserDto userDto) {

        User user = new User(userDto.getUsername(), userDto.getEmail(),
                encoder.encode(userDto.getPassword()), userDto.getFirstName(), userDto.getLastName());

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

        userRepository.save(user);

    }

    public void updateUser(UserDto userDTO) {
        Optional<User> userOptional = Optional
                .of(userRepository.findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setUsername(userDTO.getUsername());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
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
                    return user;
                });
        userRepository.save(userOptional.get());
    }

}
