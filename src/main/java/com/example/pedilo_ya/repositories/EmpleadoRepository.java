package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Restaurante.Empleado;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    @Query("SELECT c FROM Restaurante c WHERE c.id = :id")
    Optional<Empleado> findById(@Param("id") long id);
}