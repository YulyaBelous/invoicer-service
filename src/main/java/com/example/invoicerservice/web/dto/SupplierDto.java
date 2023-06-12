package com.example.invoicerservice.web.dto;

import com.example.invoicerservice.domain.*;

import java.util.Set;
import java.util.stream.Collectors;

public class SupplierDto {
    private Long id;
    private String name;
    private String shortName;
    private String fullName;
    private String taxCode;
    private Set<Invoice> invoices;
    private Set<Address> addresses;
    private Set<BankAccount> bankAccounts;
    private Set<String> availableCustomers;
    private String username;

    public SupplierDto(){}

    public SupplierDto(Supplier supplier) {

        this.id = supplier.getId();
        this.name = supplier.getName();
        this.shortName = supplier.getShortName();
        this.fullName = supplier.getFullName();
        this.taxCode = supplier.getTaxCode();
        this.invoices = supplier.getInvoices();
        this.addresses = supplier.getAddresses();
        this.bankAccounts = supplier.getBankAccounts();
        this.availableCustomers = supplier.getAvailableCustomers().stream().map(Customer::getFullName).collect(Collectors.toSet());
        this.username = supplier.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Set<String> getAvailableCustomers() {
        return availableCustomers;
    }

    public void setAvailableCustomers(Set<String> availableCustomers) {
        this.availableCustomers = availableCustomers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
