package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Customer;
import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.ICustomerRepository;
import com.example.invoicerservice.repository.IUserRepository;
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

    @Autowired
    private final IUserRepository userRepository;

    private Pageable pageable;

    public CustomerController(ICustomerRepository customerRepository, IUserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/customers")
    public Page<Customer> getAllCustomers(@RequestParam(defaultValue = "0") Integer offset,
                                          @RequestParam(defaultValue = "50") Integer limit,
                                          @RequestParam(defaultValue = "id") String sortParam,
                                          @RequestParam(defaultValue = "asc") String sortDirect,
                                          @RequestParam("username") String username) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        User user = userRepository.findByUsername(username).get();
        Boolean isCustomer = user.getAuthorities().stream()
                .filter(item -> item.getAuthority().equals("ROLE_CUSTOMER"))
                .findFirst().isPresent();
        if(isCustomer) {
            return customerRepository.findByUsername(username, pageable);
        } else {
            return customerRepository.findAll(pageable);
        }
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
