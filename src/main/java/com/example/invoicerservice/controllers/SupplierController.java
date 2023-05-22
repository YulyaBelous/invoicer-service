package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Supplier;
import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.ISupplierRepository;
import com.example.invoicerservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SupplierController {

    @Autowired
    private final ISupplierRepository supplierRepository;

    @Autowired
    private final IUserRepository userRepository;

    private Pageable pageable;

    public SupplierController(ISupplierRepository supplierRepository, IUserRepository userRepository) {
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/suppliers")
    public Page<Supplier> getAllSuppliers(@RequestParam(defaultValue = "0") Integer offset,
                                          @RequestParam(defaultValue = "25") Integer limit,
                                          @RequestParam(defaultValue = "id") String sortParam,
                                          @RequestParam(defaultValue = "asc") String sortDirect,
                                          @RequestParam("username") String username) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        User user = userRepository.findByUsername(username).get();
            Boolean isAdmin = user.getAuthorities().stream()
                    .filter(item -> item.getAuthority().equals("ROLE_ADMIN"))
                    .findFirst().isPresent();
            if(isAdmin) {
                return supplierRepository.findAll(pageable);
            } else {
                return supplierRepository.findByUsername(username, pageable);
            }
    }

    @PostMapping("/suppliers")
    public Long createSupplier(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
        return supplier.getId();
    }

    @PutMapping("/suppliers/{id}")
    public Long updateSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long id) {
        supplierRepository.save(supplier);
        return supplier.getId();
    }

    @DeleteMapping("/suppliers/{id}")
    private void deleteSupplier(@PathVariable("id") Long id)
    {
        supplierRepository.deleteById(id);
    }
}
