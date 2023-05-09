package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Supplier;
import com.example.invoicerservice.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SupplierController {

    @Autowired
    private final ISupplierRepository supplierRepository;

    private Pageable pageable;

    public SupplierController(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/suppliers")
    public Page<Supplier> getAllSuppliers(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @RequestParam("sortParam") String sortParam,
                                          @RequestParam("sortDirect") String sortDirect) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        return supplierRepository.findAll(pageable);
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
