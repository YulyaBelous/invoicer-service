package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Address;
import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.IAddressRepository;
import com.example.invoicerservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private final IAddressRepository addressRepository;

    @Autowired
    private final IUserRepository userRepository;

    private Pageable pageable;

    public AddressController(IAddressRepository addressRepository, IUserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/addresses")
    public Page<Address> getAllAddresses(@RequestParam("offset") Integer offset,
                                         @RequestParam("limit") Integer limit,
                                         @RequestParam("sortParam") String sortParam,
                                         @RequestParam("sortDirect") String sortDirect,
                                         @RequestParam("username") String username) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        User user = userRepository.findByUsername(username).get();
        Boolean isAdmin = user.getAuthorities().stream()
                .filter(item -> item.getAuthority().equals("ROLE_ADMIN"))
                .findFirst().isPresent();
        if(isAdmin) {
            return addressRepository.findAll(pageable);
        } else {
            return addressRepository.findByUsername(username, pageable);
        }
    }

    @PostMapping("/addresses")
    public Long createAddresses(@RequestBody Address address) {
        addressRepository.save(address);
        return address.getId();
    }

    @PutMapping("/addresses/{id}")
    public Long updateAddress(@RequestBody Address address, @PathVariable("id") Long id) {
        addressRepository.save(address);
        return address.getId();
    }

    @DeleteMapping("/addresses/{id}")
    private void deleteAddresses(@PathVariable("id") Long id)
    {
        addressRepository.deleteById(id);
    }
}
