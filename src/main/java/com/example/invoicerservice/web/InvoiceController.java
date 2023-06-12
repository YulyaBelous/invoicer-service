package com.example.invoicerservice.web;

import com.example.invoicerservice.domain.Invoice;
import com.example.invoicerservice.repository.InvoiceRepository;
import com.example.invoicerservice.service.ControllerService;
import com.example.invoicerservice.service.InvoiceReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceReportService invoiceReportService;
    private final ControllerService controllerService;
    private Pageable pageable;

    @Autowired
    public InvoiceController(InvoiceRepository invoiceRepository, InvoiceReportService invoiceReportService,
                             ControllerService controllerService) {

        this.invoiceRepository = invoiceRepository;
        this.invoiceReportService = invoiceReportService;
        this.controllerService = controllerService;
    }

    @GetMapping("/invoices")
    public Page<Invoice> getAllInvoices(@RequestParam(defaultValue = "0") Integer offset,
                                        @RequestParam(defaultValue = "25") Integer limit,
                                        @RequestParam(defaultValue = "id") String sortParam,
                                        @RequestParam(defaultValue = "asc") String sortDirect,
                                        @RequestParam("username") String username) {

        pageable = controllerService.getPageableWithSort(offset, limit, sortParam, sortDirect);

        if(controllerService.isAdmin(username)) {
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
    private void deleteInvoice(@PathVariable("id") Long id) {

        invoiceRepository.deleteById(id);
    }

    @GetMapping("/invoices/report/{id}")
    public String reportInvoice(@PathVariable Long id) throws FileNotFoundException, JRException {

        return invoiceReportService.exportReport(invoiceRepository.getReferenceById(id));
    }
}
