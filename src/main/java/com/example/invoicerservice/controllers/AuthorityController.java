package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Authority;
import com.example.invoicerservice.repository.IAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AuthorityController {

    @Autowired
    private IAuthorityRepository authorityRepository;

    private Pageable pageable;

    @GetMapping("/authorities")
    public Page<Authority> getAllUsers(@RequestParam("offset") Integer offset,
                                       @RequestParam("limit") Integer limit,
                                       @RequestParam("sortParam") String sortParam,
                                       @RequestParam("sortDirect") String sortDirect,
                                       @RequestParam("username") String username) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }

        return authorityRepository.findAll(pageable);
    }
}
