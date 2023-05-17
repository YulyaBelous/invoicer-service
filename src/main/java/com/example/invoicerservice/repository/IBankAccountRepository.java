package com.example.invoicerservice.repository;

import com.example.invoicerservice.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, Long> {

    Page<BankAccount> findByUsername(String username, Pageable pageable);

}
