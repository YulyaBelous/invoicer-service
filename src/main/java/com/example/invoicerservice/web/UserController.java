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

    @GetMapping("/users")
    public Page<UserDto> getAllUsers(@RequestParam(defaultValue = "0") Integer offset,
                                     @RequestParam(defaultValue = "25") Integer limit,
                                     @RequestParam(defaultValue = "id") String sortParam,
                                     @RequestParam(defaultValue = "asc") String sortDirect,
                                     @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        return userRepository.findAll(pageable).map(UserDto::new);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.saveUser(userDto);

        return ResponseEntity.ok(new MessageResponse("user registered successfully!"));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long id) {

        Optional<User> existsUsername = userRepository.findByUsername(userDto.getUsername());
        if (existsUsername.isPresent() && !existsUsername.get().getUsername().equals(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        User existsEmail = userRepository.findByEmail(userDto.getEmail());
        if (existsEmail != null && !existsEmail.getEmail().equals(userDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        userService.updateUser(userDto);

        return ResponseEntity.ok(new MessageResponse("Changes saved successfully!"));
    }

    @DeleteMapping("/users/{id}")
    private void deleteUser(@PathVariable("id") Long id) {

        userRepository.deleteById(id);
    }

}
