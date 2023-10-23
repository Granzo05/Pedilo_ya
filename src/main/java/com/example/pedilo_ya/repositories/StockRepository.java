package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Restaurante.Menu.Stock;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAll();

    @Query("SELECT s FROM Stock s WHERE s.restaurante.id = :id")
    Optional<Stock> findByIdRestaurante(@Param("id") long id);

    @Query("SELECT s FROM Stock s WHERE s.ingrediente.nombre = :nombre AND s.restaurante.id = :id")
    Optional<Stock> findByNombreProductoYRestauranteId(@Param("nombre") String nombre, @Param("id") long id);

}