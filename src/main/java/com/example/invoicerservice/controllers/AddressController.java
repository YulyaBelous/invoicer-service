package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Address;
import com.example.invoicerservice.repository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private final IAddressRepository addressRepository;

    public AddressController(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping("/addresses")
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @PostMapping("/addresses")
    public Long createAddresses(@RequestBody Address address) {
        addressRepository.save(address);
        return address.getId();
    }

    @DeleteMapping("/addresses/{id}")
    private void deleteAddresses(@PathVariable("id") Long id)
    {
        addressRepository.deleteById(id);
    }
}
