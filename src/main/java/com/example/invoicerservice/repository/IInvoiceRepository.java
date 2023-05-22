package com.example.invoicerservice.repository;

import com.example.invoicerservice.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findByUsername(String username, Pageable pageable);

}
