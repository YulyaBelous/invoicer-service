package com.example.invoicerservice.security;

import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.UserRepository;
import com.example.invoicerservice.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The CustomUserDetailsService class implements the UserDetailsService interface and is responsible for loading user
 * details from the database and returning a UserDetails object that represents the user's authentication details.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Loads user details from the database and returns a UserDetails object that represents the user's authentication
     * details.
     *
     * @param username the username of the user to load
     * @return a UserDetails object that represents the user's authentication details
     * @throws UsernameNotFoundException if the user with the specified username is not found in the database
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Find the user with the specified username in the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user Not Found with username: " + username));

        // Build a new CustomUserDetails object from the user and return it
        return CustomUserDetails.build(user);
    }

}
