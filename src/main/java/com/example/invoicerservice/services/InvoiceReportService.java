package com.example.invoicerservice.services;

import com.example.invoicerservice.entities.Invoice;
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

@Service
public class InvoiceReportService {
    public String exportReport(Invoice invoice) throws FileNotFoundException, JRException {
        String message;
        String path = "D:";
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        File file = ResourceUtils.getFile("classpath:invoiceReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoices);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Test");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        File filePDF = new File(path + "\\invoices_" + invoice.getId() + ".pdf");
        if (!filePDF.exists()) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\invoices_" + invoice.getId() + ".pdf");
            message = "Invoice successfully converted to pdf";
        } else {
            message = "Error: A report with the same name already exists";
        }
        return message;
    }
}
