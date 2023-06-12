package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.User;
import com.example.invoicerservice.repository.AuthorityRepository;
import com.example.invoicerservice.repository.UserRepository;
import com.example.invoicerservice.service.ControllerService;
import com.example.invoicerservice.web.response.MessageResponse;
import com.example.invoicerservice.service.UserService;
import com.example.invoicerservice.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The UserController class is a Spring REST controller that provides endpoints for managing users.
 * It handles HTTP requests and responses for the /api/admin/users endpoint, which allows administrators to retrieve,
 * create, update, and delete users.
 */
@RestController
@RequestMapping("/api/admin")
public class UserController {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserService userService;
    private final ControllerService controllerService;
    private Pageable pageable;

    @Autowired
    public UserController(UserRepository userRepository, AuthorityRepository authorityRepository,
                          UserService userService, ControllerService controllerService) {

        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userService = userService;
        this.controllerService = controllerService;
    }

    /**
     * Returns a page of users based on the specified query parameters and username.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the user
     * @return a page of users
     */
    @GetMapping("/users")
    public Page<UserDto> getAllUsers(@RequestParam(defaultValue = "0") Integer offset,
                                     @RequestParam(defaultValue = "25") Integer limit,
                                     @RequestParam(defaultValue = "id") String sortParam,
                                     @RequestParam(defaultValue = "asc") String sortDirect,
                                     @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        return userRepository.findAll(pageable).map(UserDto::new);
    }

    /**
     * Creates a new user with the specified details.
     *
     * @param userDto the user to create
     * @return a ResponseEntity with a message indicating whether the user was created successfully or not
     */
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        // Check if the username is already taken
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
        }

        // Check if the email is already in use
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.saveUser(userDto);

        return ResponseEntity.ok(new MessageResponse("user registered successfully!"));
    }

    /**
     * Updates an existing user with the specified ID and details.
     *
     * @param userDto the updated user
     * @param id the ID of the user to update
     * @return a ResponseEntity with a message indicating whether the changes were saved successfully or not
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long id) {

        // Check if the updated username is already taken by another user
        Optional<User> existsUsername = userRepository.findByUsername(userDto.getUsername());
        if (existsUsername.isPresent() && !existsUsername.get().getUsername().equals(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        // Check if the updated email is already in use by another user
        User existsEmail = userRepository.findByEmail(userDto.getEmail());
        if (existsEmail != null && !existsEmail.getEmail().equals(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        userService.updateUser(userDto);

        return ResponseEntity.ok(new MessageResponse("Changes saved successfully!"));
    }

    /**
     * Deletes an existing user with the specified ID.
     *
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/users/{id}")
    private void deleteUser(@PathVariable("id") Long id) {

        userRepository.deleteById(id);
    }

}
