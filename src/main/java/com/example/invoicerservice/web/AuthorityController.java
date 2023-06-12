package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Authority;
import com.example.invoicerservice.repository.AuthorityRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AuthorityController {

    private final AuthorityRepository authorityRepository;
    private final ControllerService controllerService;

    private Pageable pageable;

    @Autowired
    public AuthorityController(AuthorityRepository authorityRepository,
                               ControllerService controllerService) {

        this.authorityRepository = authorityRepository;
        this.controllerService = controllerService;
    }

    @GetMapping("/authorities")
    public Page<Authority> getAllUsers(@RequestParam(defaultValue = "0") Integer offset,
                                       @RequestParam(defaultValue = "25") Integer limit,
                                       @RequestParam(defaultValue = "id") String sortParam,
                                       @RequestParam(defaultValue = "asc") String sortDirect,
                                       @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        return authorityRepository.findAll(pageable);
    }

    @PostMapping("/authorities")
    public Long createAuthority(@RequestBody Authority authority) {

        authorityRepository.save(authority);
        return authority.getId();
    }

    @PutMapping("/authorities/{id}")
    public Long updateAuthority(@RequestBody Authority authority, @PathVariable("id") Long id) {

        authorityRepository.save(authority);
        return authority.getId();
    }

    @DeleteMapping("/authorities/{id}")
    private void deleteAuthority(@PathVariable("id") Long id) {

        authorityRepository.deleteById(id);
    }
}
