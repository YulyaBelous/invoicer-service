package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.Invoice;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceReportServiceTest {

    @InjectMocks
    private InvoiceReportService invoiceReportService;

    @Test
    @DisplayName("Should return an error message when the file with the same name already exists")
    void exportReportWhenFileAlreadyExists() throws FileNotFoundException, JRException {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setNumber("INV-001");
        invoice.setDescription("Test invoice");
        invoice.setUnitPrice(10.0);
        invoice.setQuantity(5);
        invoice.setAmount(50.0);

        File filePDF = new File("D:\\invoices_1.pdf");
        try {
            filePDF.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = invoiceReportService.exportReport(invoice);

        assertEquals("Error: A report with the same name already exists", result);

        filePDF.delete();
    }

    @Test
    @DisplayName("Should export the report and return success message when the file does not exist")
    void exportReportWhenFileDoesNotExist() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setNumber("INV-001");
        invoice.setDate(LocalDate.now());
        invoice.setDescription("Test invoice");
        invoice.setUnitPrice(10.0);
        invoice.setQuantity(5);
        invoice.setAmount(50.0);

        try {
            String result = invoiceReportService.exportReport(invoice);
            File file = new File("D:\\invoices_1.pdf");
            assertTrue(file.exists());
            assertEquals("Invoice successfully converted to pdf", result);
        } catch (FileNotFoundException | JRException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

}