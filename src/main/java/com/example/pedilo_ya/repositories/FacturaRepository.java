package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Factura.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findAll();

    @Query("SELECT * FROM Factura WHERE id_cliente = :id")
    List<Factura> findByIdCliente(@Param("id") long id);

    @Query("SELECT * FROM Factura WHERE id = :id")
    Optional<Factura> findById(@Param("id") long id);

    @Query("SELECT * FROM Factura WHERE id_detalle_factura = :id")
    Optional<Factura> findByIdDetalleFactura(@Param("id") long id);
}