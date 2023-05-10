package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Invoice;
import com.example.invoicerservice.repository.IInvoiceRepository;
import com.example.invoicerservice.services.InvoiceReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private final IInvoiceRepository invoiceRepository;

    private Pageable pageable;

    @Autowired
    private InvoiceReportService invoiceReportService;

    public InvoiceController(IInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/invoices")
    public Page<Invoice> getAllInvoices(@RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit,
                                        @RequestParam("sortParam") String sortParam,
                                        @RequestParam("sortDirect") String sortDirect) {
        if(sortDirect.equals("asc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).ascending());
        } else if(sortDirect.equals("desc")) {
            pageable = PageRequest.of(offset, limit, Sort.by(sortParam).descending());
        }
        return invoiceRepository.findAll(pageable);
    }

   @PostMapping("/invoices")
    public Long createInvoice(@RequestBody Invoice invoice) {
        invoiceRepository.save(invoice);
        return invoice.getId();
    }

    @PutMapping("/invoices/{id}")
    public Long updateInvoice(@RequestBody Invoice invoice, @PathVariable("id") Long id) {
        invoiceRepository.save(invoice);
        return invoice.getId();
    }

    @DeleteMapping("/invoices/{id}")
    private void deleteInvoice(@PathVariable("id") Long id)
    {
        invoiceRepository.deleteById(id);
    }

    @GetMapping("/invoices/report/{id}")
    public String reportInvoice(@PathVariable Long id) throws FileNotFoundException, JRException {
        String message = invoiceReportService.exportReport(invoiceRepository.getReferenceById(id));
        return message;
    }
}
