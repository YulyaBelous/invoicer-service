package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Customer;
import com.example.invoicerservice.repository.CustomerRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final ControllerService controllerService;
    private Pageable pageable;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, ControllerService controllerService) {

        this.customerRepository = customerRepository;
        this.controllerService = controllerService;
    }

    @GetMapping("/customers")
    public Page<Customer> getAllCustomers(@RequestParam(defaultValue = "0") Integer offset,
                                          @RequestParam(defaultValue = "25") Integer limit,
                                          @RequestParam(defaultValue = "id") String sortParam,
                                          @RequestParam(defaultValue = "asc") String sortDirect,
                                          @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        if(controllerService.isCustomer(username)) {
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
    private void deleteCustomer(@PathVariable("id") Long id) {

        customerRepository.deleteById(id);
    }
}
