package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Address;
import com.example.invoicerservice.repository.AddressRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * The AddressController class is a Spring REST controller that provides endpoints for managing addresses.
 * It handles HTTP requests and responses for the /api/addresses endpoint, which allows users to retrieve, create,
 * update, and delete addresses.
 */
@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressRepository addressRepository;
    private final ControllerService controllerService;
    private Pageable pageable;

    @Autowired
    public AddressController(AddressRepository addressRepository, ControllerService controllerService) {

        this.addressRepository = addressRepository;
        this.controllerService = controllerService;
    }

    /**
     * Returns a page of addresses based on the specified query parameters. If the user is an admin, all addresses
     * are returned. Otherwise, only addresses belonging to the specified user are returned.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the user
     * @return a page of addresses
     */
    @GetMapping("/addresses")
    public Page<Address> getAllAddresses(@RequestParam(defaultValue = "0") Integer offset,
                                         @RequestParam(defaultValue = "25") Integer limit,
                                         @RequestParam(defaultValue = "id") String sortParam,
                                         @RequestParam(defaultValue = "asc") String sortDirect,
                                         @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        if(controllerService.isAdmin(username)) {
            return addressRepository.findAll(pageable);
        } else {
            return addressRepository.findByUsername(username, pageable);
        }
    }

    /**
     * Creates a new address with the specified details.
     *
     * @param address the address to create
     * @return the ID of the created address
     */
    @PostMapping("/addresses")
    public Long createAddresses(@RequestBody Address address) {

        addressRepository.save(address);
        return address.getId();
    }

    /**
     * Updates an existing address with the specified ID and details.
     *
     * @param address the updated address
     * @param id the ID of the address to update
     * @return the ID of the updated address
     */
    @PutMapping("/addresses/{id}")
    public Long updateAddress(@RequestBody Address address, @PathVariable("id") Long id) {

        addressRepository.save(address);
        return address.getId();
    }

    /**
     * Deletes an existing address with the specified ID.
     *
     * @param id the ID of the address to delete
     */
    @DeleteMapping("/addresses/{id}")
    private void deleteAddresses(@PathVariable("id") Long id) {

        addressRepository.deleteById(id);
    }
}
