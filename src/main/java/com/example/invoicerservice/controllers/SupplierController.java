package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Supplier;
import com.example.invoicerservice.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SupplierController {

    @Autowired
    private final ISupplierRepository supplierRepository;

    public SupplierController(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/suppliers")
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @PostMapping("/suppliers")
    public Long createSupplier(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
        return supplier.getId();
    }

    @DeleteMapping("/suppliers/{id}")
    private void deleteSupplier(@PathVariable("id") Long id)
    {
        supplierRepository.deleteById(id);
    }
}
