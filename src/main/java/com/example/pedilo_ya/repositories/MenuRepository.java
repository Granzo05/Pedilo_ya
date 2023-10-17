package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m FROM Menu m WHERE m.restaurante.id = :id")
    List<Menu> findByIdRestaurante(@Param("id") long id);
}