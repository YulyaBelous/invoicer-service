package com.example.invoicerservice.entities;

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

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn
    private Address address;

    @OneToMany(mappedBy = "bankAccountCustomer")
    private Set<Invoice> invoicesCustomer = new HashSet<>();

    @OneToMany(mappedBy = "bankAccountSupplier")
    private Set<Invoice> invoicesSupplier = new HashSet<>();
}
