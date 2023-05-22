package com.example.invoicerservice.controllers;

import com.example.invoicerservice.entities.Invoice;
import com.example.invoicerservice.entities.User;
import com.example.invoicerservice.repository.IInvoiceRepository;
import com.example.invoicerservice.repository.IUserRepository;
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

    @Autowired
    private final IUserRepository userRepository;

    private Pageable pageable;

    @Autowired
    private InvoiceReportService invoiceReportService;

    public InvoiceController(IInvoiceRepository invoiceRepository, IUserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/invoices")
    public Page<Invoice> getAllInvoices(@RequestParam(defaultValue = "0") Integer offset,
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
            return invoiceRepository.findAll(pageable);
        } else {
            return invoiceRepository.findByUsername(username, pageable);
        }
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
