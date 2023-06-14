package com.example.invoicerservice.service;

import com.example.invoicerservice.domain.Invoice;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceReportServiceTest {

    @InjectMocks
    private InvoiceReportService invoiceReportService;

    private Invoice invoice;

    static final String PATH = "D:\\invoices_1.pdf";

    @BeforeEach
    void setUp() {

        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setNumber("INV-001");
        invoice.setDescription("Test invoice");
        invoice.setUnitPrice(10.0);
        invoice.setQuantity(5);
        invoice.setAmount(50.0);

    }

    @Test
    @DisplayName("Should return an error message when the file with the same name already exists")
    void exportReportWhenFileAlreadyExists() throws FileNotFoundException, JRException {

        // Set up a file with the same name as the one we're going to create
        File filePDF = new File(PATH);
        try {
            filePDF.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call the exportReport method of InvoiceReportService with the invoice
        String result = invoiceReportService.exportReport(invoice);

        //Verify that it returns the correct error message
        assertEquals("Error: A report with the same name already exists", result);

        // Delete the file we created
        filePDF.delete();
    }

    @Test
    @DisplayName("Should export the report and return success message when the file does not exist")
    void exportReportWhenFileDoesNotExist() {

        try {

            // Call the exportReport method of InvoiceReportService with the invoice
            String result = invoiceReportService.exportReport(invoice);
            File file = new File(PATH);

            //Verify that it exports the report successfully and returns the correct success message
            assertTrue(file.exists());
            assertEquals("Invoice successfully converted to pdf", result);

        } catch (FileNotFoundException | JRException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

}