package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Address;
import com.example.invoicerservice.repository.AddressRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    private void deleteAddresses(@PathVariable("id") Long id) {

        addressRepository.deleteById(id);
    }
}
