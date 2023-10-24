package com.example.pedilo_ya.repositories;

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
    Optional<Restaurante> findById(@Param("id") long id);

    @Query("SELECT r FROM Restaurante r WHERE r.email = :email")
    Optional<Restaurante> findByEmail(@Param("email") String email);

    @Query("SELECT r FROM Restaurante r WHERE r.email = :email AND r.contraseña = :contraseña")
    Optional<Restaurante> findByEmailAndPassword(@Param("email") String email, @Param("contraseña") String password);

}