package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Customer;
import com.example.invoicerservice.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private final ICustomerRepository customerRepository;

    public CustomerController(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Long createCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customer.getId();
    }

    @DeleteMapping("/customers/{id}")
    private void deleteCustomer(@PathVariable("id") Long id)
    {
        customerRepository.deleteById(id);
    }
}
