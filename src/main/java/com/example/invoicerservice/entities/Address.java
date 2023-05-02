package com.example.invoicerservice.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "addressCustomer")
    private Set<Invoice> invoicesCustomer = new HashSet<>();

    @OneToMany(mappedBy = "addressSupplier")
    private Set<Invoice> invoicesSupplier = new HashSet<>();
}
