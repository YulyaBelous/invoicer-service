package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.Invoice;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The InvoiceReportService class is responsible for generating a PDF report for a given invoice using JasperReports.
 */
@Service
public class InvoiceReportService {

    /**
     * Generates a PDF report for the specified invoice using JasperReports.
     *
     * @param invoice the invoice to generate the report for
     * @return a message indicating whether the report was successfully generated or not
     * @throws FileNotFoundException if the invoiceReport.jrxml file is not found
     * @throws JRException            if an error occurs while generating the report
     */
    public String exportReport(Invoice invoice) throws FileNotFoundException, JRException {

        String path = "D:";

        // Create a list of invoices containing only the specified invoice
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);

        // Load the invoiceReport.jrxml file and compile it into a JasperReport
        File file = ResourceUtils.getFile("classpath:invoiceReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // Create a JRBeanCollectionDataSource object from the list of invoices
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoices);

        // Set the parameters for the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Test");

        // Fill the report with data and create a JasperPrint object
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Create a new File object for the PDF report
        File filePDF = new File(path + "\\invoices_" + invoice.getId() + ".pdf");

        // Check if a report with the same name already exists
        if (!filePDF.exists()) {

            // Export the report to a PDF file and return a success message
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\invoices_" + invoice.getId() + ".pdf");
            return "Invoice successfully converted to pdf";
        } else {

            // Return an error message if a report with the same name already exists
            return "Error: A report with the same name already exists";
        }
    }
}
