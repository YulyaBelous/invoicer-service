package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Customer;
import com.example.invoicerservice.repository.CustomerRepository;
import com.example.invoicerservice.service.ControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * The CustomerController class is a Spring REST controller that provides endpoints for managing customers.
 * It handles HTTP requests and responses for the /api/customers endpoint, which allows users to retrieve, create,
 * update, and delete customers.
 */
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

    /**
     * Returns a page of customers based on the specified query parameters and username.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the user
     * @return a page of customers
     */
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

    /**
     * Creates a new customer with the specified details.
     *
     * @param customer the customer to create
     * @return the ID of the created customer
     */
    @PostMapping("/customers")
    public Long createCustomer(@RequestBody Customer customer) {

        customerRepository.save(customer);
        return customer.getId();
    }

    /**
     * Updates an existing customer with the specified ID and details.
     *
     * @param customer the updated customer
     * @param id the ID of the customer to update
     * @return the ID of the updated customer
     */
    @PutMapping("/customers/{id}")
    public Long updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id) {

        customerRepository.save(customer);
        return customer.getId();
    }

    /**
     * Deletes an existing customer with the specified ID.
     *
     * @param id the ID of the customer to delete
     */
    @DeleteMapping("/customers/{id}")
    private void deleteCustomer(@PathVariable("id") Long id) {

        customerRepository.deleteById(id);
    }
}
