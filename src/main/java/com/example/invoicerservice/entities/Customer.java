package com.example.invoicerservice.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "customer")
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<BankAccount> bankAccounts = new HashSet<>();
}
