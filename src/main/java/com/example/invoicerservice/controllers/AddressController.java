package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Address;
import com.example.invoicerservice.repository.IAddressRepository;
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

    private Pageable pageable;

    public AddressController(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping("/addresses")
    public Page<Address> getAllAddresses(@RequestParam("offset") Integer offset,
                                         @RequestParam("limit") Integer limit,
                                         @RequestParam("sortParam") String sortParam,
                                         @RequestParam("sortDirect") String sortDirect) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        return addressRepository.findAll(pageable);
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
