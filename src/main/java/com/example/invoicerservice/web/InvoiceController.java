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

/**
 * The InvoiceController class is a Spring REST controller that provides endpoints for managing invoices.
 * It handles HTTP requests and responses for the /api/invoices endpoint, which allows users to retrieve, create,
 * update, and delete invoices, as well as generate reports for individual invoices.
 */
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

    /**
     * Returns a page of invoices based on the specified query parameters and username.
     *
     * @param offset the offset of the page
     * @param limit the limit of the page
     * @param sortParam the parameter to sort by
     * @param sortDirect the direction to sort in
     * @param username the username of the user
     * @return a page of invoices
     */
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

    /**
     * Creates a new invoice with the specified details.
     *
     * @param invoice the invoice to create
     * @return the ID of the created invoice
     */
   @PostMapping("/invoices")
    public Long createInvoice(@RequestBody Invoice invoice) {

        invoiceRepository.save(invoice);
        return invoice.getId();
    }

    /**
     * Updates an existing invoice with the specified ID and details.
     *
     * @param invoice the updated invoice
     * @param id the ID of the invoice to update
     * @return the ID of the updated invoice
     */
    @PutMapping("/invoices/{id}")
    public Long updateInvoice(@RequestBody Invoice invoice, @PathVariable("id") Long id) {

        invoiceRepository.save(invoice);
        return invoice.getId();
    }

    /**
     * Deletes an existing invoice with the specified ID.
     *
     * @param id the ID of the invoice to delete
     */
    @DeleteMapping("/invoices/{id}")
    private void deleteInvoice(@PathVariable("id") Long id) {

        invoiceRepository.deleteById(id);
    }

    /**
     * Generates a report for the invoice with the specified ID.
     *
     * @param id the ID of the invoice to generate a report for
     * @return the report as a string
     * @throws FileNotFoundException if the report file is not found
     * @throws JRException if there is an error generating the report
     */
    @GetMapping("/invoices/report/{id}")
    public String reportInvoice(@PathVariable Long id) throws FileNotFoundException, JRException {

        return invoiceReportService.exportReport(invoiceRepository.getReferenceById(id));
    }
}
