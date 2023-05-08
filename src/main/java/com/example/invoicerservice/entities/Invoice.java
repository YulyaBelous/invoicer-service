package com.example.invoicerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties(value = { "address", "bankAccounts", "invoices" }, allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties(value = { "address", "bankAccounts", "invoices" }, allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_account_supplier_id")
    @JsonIgnoreProperties(value = { "invoicesCustomer", "invoicesSupplier" }, allowSetters = true)
    private BankAccount bankAccountSupplier;

    @ManyToOne
    @JoinColumn(name = "bank_account_customer_id")
    @JsonIgnoreProperties(value = { "invoicesCustomer", "invoicesSupplier" }, allowSetters = true)
    private BankAccount bankAccountCustomer;

    @ManyToOne
    @JoinColumn(name = "address_supplier_id")
    @JsonIgnoreProperties(value = { "invoicesCustomer", "invoicesSupplier" }, allowSetters = true)
    private Address addressSupplier;

    @ManyToOne
    @JoinColumn(name = "address_customer_id")
    @JsonIgnoreProperties(value = { "invoicesCustomer", "invoicesSupplier" }, allowSetters = true)
    private Address addressCustomer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public BankAccount getBankAccountSupplier() {
        return bankAccountSupplier;
    }

    public void setBankAccountSupplier(BankAccount bankAccountSupplier) {
        this.bankAccountSupplier = bankAccountSupplier;
    }

    public BankAccount getBankAccountCustomer() {
        return bankAccountCustomer;
    }

    public void setBankAccountCustomer(BankAccount bankAccountCustomer) {
        this.bankAccountCustomer = bankAccountCustomer;
    }

    public Address getAddressSupplier() {
        return addressSupplier;
    }

    public void setAddressSupplier(Address addressSupplier) {
        this.addressSupplier = addressSupplier;
    }

    public Address getAddressCustomer() {
        return addressCustomer;
    }

    public void setAddressCustomer(Address addressCustomer) {
        this.addressCustomer = addressCustomer;
    }
}
