package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Invoice;
import com.example.invoicerservice.repository.IInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private final IInvoiceRepository invoiceRepository;

    public InvoiceController(IInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

   @PostMapping("/invoices")
    public Long createInvoice(@RequestBody Invoice invoice) {
        invoiceRepository.save(invoice);
        return invoice.getId();
    }

    @DeleteMapping("/invoices/{id}")
    private void deleteInvoice(@PathVariable("id") Long id)
    {
        invoiceRepository.deleteById(id);
    }
}
