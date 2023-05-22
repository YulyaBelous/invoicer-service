package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Authority;
import com.example.invoicerservice.entities.Supplier;
import com.example.invoicerservice.repository.IAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AuthorityController {

    @Autowired
    private IAuthorityRepository authorityRepository;

    private Pageable pageable;

    @GetMapping("/authorities")
    public Page<Authority> getAllUsers(@RequestParam(defaultValue = "0") Integer offset,
                                       @RequestParam(defaultValue = "25") Integer limit,
                                       @RequestParam(defaultValue = "id") String sortParam,
                                       @RequestParam(defaultValue = "asc") String sortDirect,
                                       @RequestParam("username") String username) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }

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
    private void deleteAuthority(@PathVariable("id") Long id)
    {
        authorityRepository.deleteById(id);
    }
}
