package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAll();
    @Query("SELECT * FROM Cliente WHERE email = :email")
    Optional<Cliente> findByEmail(@Param("email") String email);
}