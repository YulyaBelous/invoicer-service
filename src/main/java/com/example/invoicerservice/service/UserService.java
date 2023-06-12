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

    public void saveUser(UserDto userDto) {

        User user = new User(userDto.getUsername(), userDto.getEmail(),
                encoder.encode(userDto.getPassword()), userDto.getFirstName(), userDto.getLastName(), userDto.isActivated());

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
                    return user;
                });
        userRepository.save(userOptional.get());
    }

}
