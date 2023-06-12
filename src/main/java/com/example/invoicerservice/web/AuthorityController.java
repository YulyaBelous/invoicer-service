package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Authority;
import com.example.invoicerservice.repository.AuthorityRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * The AuthorityController class is a Spring REST controller that provides endpoints for managing authorities.
 * It handles HTTP requests and responses for the /api/admin/authorities endpoint, which allows admins to retrieve,
 * create, update, and delete authorities.
 */
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

    /**
     * Returns a page of authorities based on the specified query parameters.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the admin
     * @return a page of authorities
     */
    @GetMapping("/authorities")
    public Page<Authority> getAllUsers(@RequestParam(defaultValue = "0") Integer offset,
                                       @RequestParam(defaultValue = "25") Integer limit,
                                       @RequestParam(defaultValue = "id") String sortParam,
                                       @RequestParam(defaultValue = "asc") String sortDirect,
                                       @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        return authorityRepository.findAll(pageable);
    }

    /**
     * Creates a new authority with the specified details.
     *
     * @param authority the authority to create
     * @return the ID of the created authority
     */
    @PostMapping("/authorities")
    public Long createAuthority(@RequestBody Authority authority) {

        authorityRepository.save(authority);
        return authority.getId();
    }

    /**
     * Updates an existing authority with the specified ID and details.
     *
     * @param authority the updated authority
     * @param id the ID of the authority to update
     * @return the ID of the updated authority
     */
    @PutMapping("/authorities/{id}")
    public Long updateAuthority(@RequestBody Authority authority, @PathVariable("id") Long id) {

        authorityRepository.save(authority);
        return authority.getId();
    }

    /**
     * Deletes an existing authority with the specified ID.
     *
     * @param id the ID of the authority to delete
     */
    @DeleteMapping("/authorities/{id}")
    private void deleteAuthority(@PathVariable("id") Long id) {

        authorityRepository.deleteById(id);
    }
}
