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

    @PostMapping("/suppliers")
    public Long createSupplier(@RequestBody Supplier supplier) {

        supplierRepository.save(supplier);
        return supplier.getId();
    }

    @PutMapping("/suppliers/{id}")
    public Long updateSupplier(@RequestBody SupplierDto supplierDto, @PathVariable("id") Long id) {

        Optional<Supplier> supplierOptional = Optional
                .of(supplierRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(supplier -> {
                    supplier.setName(supplierDto.getName());
                    supplier.setShortName(supplierDto.getShortName());
                    supplier.setFullName(supplierDto.getFullName());
                    supplier.setTaxCode(supplierDto.getTaxCode());
                    supplier.setUsername(supplierDto.getUsername());
                    supplier.setInvoices(new HashSet(supplierDto.getInvoices()));
                    supplier.setAddresses(new HashSet(supplierDto.getAddresses()));
                    supplier.setBankAccounts(new HashSet(supplierDto.getBankAccounts()));
                    Set<Customer> managedAvailableCustomer = supplier.getAvailableCustomers();
                    managedAvailableCustomer.clear();
                    supplierDto
                            .getAvailableCustomers()
                            .stream()
                            .map(customerRepository::findByFullName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAvailableCustomer::add);
                    return supplier;
                });

        supplierRepository.save(supplierOptional.get());
        return supplierOptional.get().getId();
    }

    @DeleteMapping("/suppliers/{id}")
    private void deleteSupplier(@PathVariable("id") Long id) {

        supplierRepository.deleteById(id);
    }
}
