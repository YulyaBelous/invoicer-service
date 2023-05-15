package com.example.invoicerservice.repository;

import com.example.invoicerservice.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
}
