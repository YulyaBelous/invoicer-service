package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.IUserRepository;
import com.example.invoicerservice.services.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    IUserRepository userRepository;

    private Pageable pageable;

    @GetMapping("/users")
    public Page<UserDto> getAllUsers(@RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit,
                                        @RequestParam("sortParam") String sortParam,
                                        @RequestParam("sortDirect") String sortDirect,
                                        @RequestParam("username") String username) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }

        return userRepository.findAll(pageable).map(UserDto::new);
    }

    @PostMapping("/users")
    public Long createUser(@RequestBody User user) {
        userRepository.save(user);
        return user.getId();
    }

    @PutMapping("/users/{id}")
    public Long updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        userRepository.save(user);
        return user.getId();
    }

    @DeleteMapping("/users/{id}")
    private void deleteUser(@PathVariable("id") Long id)
    {
        userRepository.deleteById(id);
    }

}
