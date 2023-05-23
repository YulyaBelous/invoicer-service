package com.example.invoicerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "tax_code")
    private String taxCode;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnoreProperties(value = { "supplier", "customer" }, allowSetters = true)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    @JsonIgnoreProperties(value = { "supplier", "customer" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    @JsonIgnoreProperties(value = { "supplier", "customer" }, allowSetters = true)
    private Set<BankAccount> bankAccounts = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name="available_customer",
            joinColumns = { @JoinColumn(name="supplier_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name="customer_name", referencedColumnName="name") } )
    private Set<Customer> availableCustomers = new HashSet<>();

    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Customer> getAvailableCustomers() {
        return availableCustomers;
    }

    public void setAvailableCustomers(Set<Customer> availableCustomers) {
        this.availableCustomers = availableCustomers;
    }
}
