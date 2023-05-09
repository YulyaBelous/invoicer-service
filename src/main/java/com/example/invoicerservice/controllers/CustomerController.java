package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Customer;
import com.example.invoicerservice.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private final ICustomerRepository customerRepository;

    private Pageable pageable;

    public CustomerController(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public Page<Customer> getAllCustomers(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @RequestParam("sortParam") String sortParam,
                                          @RequestParam("sortDirect") String sortDirect) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        return customerRepository.findAll(pageable);
    }
    @PostMapping("/customers")
    public Long createCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customer.getId();
    }

    @PutMapping("/customers/{id}")
    public Long updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id) {
        customerRepository.save(customer);
        return customer.getId();
    }

    @DeleteMapping("/customers/{id}")
    private void deleteCustomer(@PathVariable("id") Long id)
    {
        customerRepository.deleteById(id);
    }
}
