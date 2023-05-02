package com.example.invoicerservice.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_account_supplier_id")
    private BankAccount bankAccountSupplier;

    @ManyToOne
    @JoinColumn(name = "bank_account_customer_id")
    private BankAccount bankAccountCustomer;

    @ManyToOne
    @JoinColumn(name = "address_supplier_id")
    private Address addressSupplier;

    @ManyToOne
    @JoinColumn(name = "address_customer_id")
    private Address addressCustomer;
}
