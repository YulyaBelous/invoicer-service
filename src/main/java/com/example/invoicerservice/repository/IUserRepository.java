package com.example.invoicerservice.repository;

import com.example.invoicerservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
