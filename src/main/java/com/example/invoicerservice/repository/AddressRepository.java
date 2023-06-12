package com.example.invoicerservice.repository;

import com.example.invoicerservice.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.username = :username")
    Page<Address> findByUsername(@Param("username") String username, Pageable pageable);
}
