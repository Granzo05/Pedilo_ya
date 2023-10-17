package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findAll();
    @Query("SELECT r FROM Restaurante r WHERE r.id = :id")
    Optional<Factura> findById(@Param("id") long id);

}