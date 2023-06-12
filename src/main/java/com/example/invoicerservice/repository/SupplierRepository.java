package com.example.invoicerservice.repository;

import com.example.invoicerservice.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s WHERE s.username = :username")
    Page<Supplier> findByUsername(@Param("username") String username, Pageable pageable);
}
