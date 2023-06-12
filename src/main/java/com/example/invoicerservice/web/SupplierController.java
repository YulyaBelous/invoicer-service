package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Customer;
import com.example.invoicerservice.domain.Supplier;
import com.example.invoicerservice.repository.CustomerRepository;
import com.example.invoicerservice.repository.SupplierRepository;
import com.example.invoicerservice.service.ControllerService;
import com.example.invoicerservice.web.dto.SupplierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The SupplierController class is a Spring REST controller that provides endpoints for managing suppliers.
 * It handles HTTP requests and responses for the /api/suppliers endpoint, which allows users to retrieve, create,
 * update, and delete suppliers.
 */
@RestController
@RequestMapping("/api")
public class SupplierController {

    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final ControllerService controllerService;
    private Pageable pageable;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository, CustomerRepository customerRepository,
                              ControllerService controllerService) {

        this.supplierRepository = supplierRepository;
        this.customerRepository = customerRepository;
        this.controllerService = controllerService;
    }

    /**
     * Returns a page of suppliers based on the specified query parameters and username.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the user
     * @return a page of suppliers
     */
    @GetMapping("/suppliers")
    public Page<SupplierDto> getAllSuppliers(@RequestParam(defaultValue = "0") Integer offset,
                                             @RequestParam(defaultValue = "25") Integer limit,
                                             @RequestParam(defaultValue = "id") String sortParam,
                                             @RequestParam(defaultValue = "asc") String sortDirect,
                                             @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        if (controllerService.isAdmin(username)) {
            return supplierRepository.findAll(pageable).map(SupplierDto::new);
        } else {
            return supplierRepository.findByUsername(username, pageable).map(SupplierDto::new);
        }
    }

    /**
     * Creates a new supplier with the specified details.
     *
     * @param supplier the supplier to create
     * @return the ID of the created supplier
     */
    @PostMapping("/suppliers")
    public Long createSupplier(@RequestBody Supplier supplier) {

        supplierRepository.save(supplier);
        return supplier.getId();
    }

    /**
     * Updates an existing supplier with the specified ID and details.
     *
     * @param supplierDto the updated supplier
     * @param id the ID of the supplier to update
     * @return the ID of the updated supplier
     */
    @PutMapping("/suppliers/{id}")
    public Long updateSupplier(@RequestBody SupplierDto supplierDto, @PathVariable("id") Long id) {

        // Find the supplier with the specified ID in the database
        Optional<Supplier> supplierOptional = Optional
                .of(supplierRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(supplier -> {

                    // Update the supplier details with the values from the SupplierDto object
                    supplier.setName(supplierDto.getName());
                    supplier.setShortName(supplierDto.getShortName());
                    supplier.setFullName(supplierDto.getFullName());
                    supplier.setTaxCode(supplierDto.getTaxCode());
                    supplier.setUsername(supplierDto.getUsername());
                    supplier.setInvoices(new HashSet(supplierDto.getInvoices()));
                    supplier.setAddresses(new HashSet(supplierDto.getAddresses()));
                    supplier.setBankAccounts(new HashSet(supplierDto.getBankAccounts()));

                    // Update the available customers for the supplier
                    Set<Customer> managedAvailableCustomer = supplier.getAvailableCustomers();
                    managedAvailableCustomer.clear();
                    supplierDto
                            .getAvailableCustomers()
                            .stream()
                            .map(customerRepository::findByFullName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAvailableCustomer::add);

                    // Return the updated supplier object
                    return supplier;
                });

        // Save the updated supplier object to the database and return its ID
        supplierRepository.save(supplierOptional.get());
        return supplierOptional.get().getId();
    }

    /**
     * Deletes an existing supplier with the specified ID.
     *
     * @param id the ID of the supplier to delete
     */
    @DeleteMapping("/suppliers/{id}")
    private void deleteSupplier(@PathVariable("id") Long id) {

        supplierRepository.deleteById(id);
    }
}
