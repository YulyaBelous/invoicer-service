package com.example.invoicerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "swift")
    private String swift;

    @Column(name = "correspondent_name")
    private String correspondentName;

    @Column(name = "correspondent_address")
    private String correspondentAddress;

    @Column(name = "correspondent_swift")
    private String correspondentSwift;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties(value = { "address", "bankAccounts", "invoices" }, allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties(value = { "address", "bankAccounts", "invoices" }, allowSetters = true)
    private Customer customer;

    @OneToOne
    @JoinColumn
    private Address address;

    @OneToMany(mappedBy = "bankAccountCustomer")
    @JsonIgnoreProperties(value = { "supplier", "customer", "bankAccountSupplier", "bankAccountCustomer", "addressSupplier", "addressCustomer" }, allowSetters = true)
    private Set<Invoice> invoicesCustomer = new HashSet<>();

    @OneToMany(mappedBy = "bankAccountSupplier")
    @JsonIgnoreProperties(value = { "supplier", "customer", "bankAccountSupplier", "bankAccountCustomer", "addressSupplier", "addressCustomer" }, allowSetters = true)
    private Set<Invoice> invoicesSupplier = new HashSet<>();

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getCorrespondentName() {
        return correspondentName;
    }

    public void setCorrespondentName(String correspondentName) {
        this.correspondentName = correspondentName;
    }

    public String getCorrespondentAddress() {
        return correspondentAddress;
    }

    public void setCorrespondentAddress(String correspondentAddress) {
        this.correspondentAddress = correspondentAddress;
    }

    public String getCorrespondentSwift() {
        return correspondentSwift;
    }

    public void setCorrespondentSwift(String correspondentSwift) {
        this.correspondentSwift = correspondentSwift;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Invoice> getInvoicesCustomer() {
        return invoicesCustomer;
    }

    public void setInvoicesCustomer(Set<Invoice> invoicesCustomer) {
        this.invoicesCustomer = invoicesCustomer;
    }

    public Set<Invoice> getInvoicesSupplier() {
        return invoicesSupplier;
    }

    public void setInvoicesSupplier(Set<Invoice> invoicesSupplier) {
        this.invoicesSupplier = invoicesSupplier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
