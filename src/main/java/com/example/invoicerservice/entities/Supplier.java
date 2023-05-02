package com.example.invoicerservice.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "supplier")
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    private Set<BankAccount> bankAccounts = new HashSet<>();



}
