package com.example.invoicerservice.repository;

import com.example.invoicerservice.domain.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("SELECT ba FROM BankAccount ba WHERE ba.username = :username")
    Page<BankAccount> findByUsername(@Param("username") String username, Pageable pageable);
}
