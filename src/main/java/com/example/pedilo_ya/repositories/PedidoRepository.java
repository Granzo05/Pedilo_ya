package com.example.pedilo_ya.repositories;

import com.example.pedilo_ya.entities.Pedidos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :id")
    List<Pedido> findByIdCliente(@Param("id") long id);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :id")
    List<Pedido> findByIdNegocio(@Param("id") long id);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :id AND p.estado = 'procesado'")
    List<Pedido> findPedidosProcesadosByRestaurante(@Param("id") Long restauranteId);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :id AND p.estado = 'aceptado'")
    List<Pedido> findPedidosAceptadosACocina(@Param("id") Long restauranteId);
}

