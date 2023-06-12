package com.example.invoicerservice.web.dto;

import com.example.invoicerservice.domain.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The SupplierDto class represents a data transfer object containing information about a supplier. It is used by the
 * supplier controller to send supplier data to the client.
 */
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

    /**
     * Constructs a new SupplierDto object with default values for all fields.
     */
    public SupplierDto() {}

    /**
     * Constructs a new SupplierDto object with the specified supplier data.
     *
     * @param supplier the supplier object to extract data from
     */
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

    /**
     * Returns the supplier ID.
     *
     * @return the supplier ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the supplier ID.
     *
     * @param id the supplier ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the supplier name.
     *
     * @return the supplier name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the supplier name.
     *
     * @param name the supplier name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the supplier short name.
     *
     * @return the supplier short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the supplier short name.
     *
     * @param shortName the supplier short name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Returns the supplier full name.
     *
     * @return the supplier full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the supplier full name.
     *
     * @param fullName the supplier full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the supplier tax code.
     *
     * @return the supplier tax code
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * Sets the supplier tax code.
     *
     * @param taxCode the supplier tax code
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    /**
     * Returns the supplier invoices.
     *
     * @return the supplier invoices
     */
    public Set<Invoice> getInvoices() {
        return invoices;
    }

    /**
     * Sets the supplier invoices.
     *
     * @param invoices the supplier invoices
     */
    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    /**
     * Returns the supplier addresses.
     *
     * @return the supplier addresses
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * Sets the supplier addresses.
     *
     * @param addresses the supplier addresses
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * Returns the supplier bank accounts.
     *
     * @return the supplier bank accounts
     */
    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * Sets the supplier bank accounts.
     *
     * @param bankAccounts the supplier bank accounts
     */
    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    /**
     * Returns the names of the customers available to the supplier.
     *
     * @return the names of the customers available to the supplier
     */
    public Set<String> getAvailableCustomers() {
        return availableCustomers;
    }

    /**
     * Sets the names of the customers available to the supplier.
     *
     * @param availableCustomers the names of the customers available to the supplier
     */
    public void setAvailableCustomers(Set<String> availableCustomers) {
        this.availableCustomers = availableCustomers;
    }

    /**
     * Returns the username of the supplier.
     *
     * @return the username of the supplier
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the supplier.
     *
     * @param username the username of the supplier
     */
    public void setUsername(String username) {
        this.username = username;
    }
}