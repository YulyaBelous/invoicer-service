package com.example.invoicerservice.repository;

import com.example.invoicerservice.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.username = :username")
    Page<Customer> findByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.fullName = :fullName")
    Optional<Customer> findByFullName(@Param("fullName") String fullName);
}
