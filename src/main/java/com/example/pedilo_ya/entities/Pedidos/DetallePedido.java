package com.example.pedilo_ya.entities.Pedidos;

import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {
    @Column(name = "fecha", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaPedido;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cantidad")
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    public DetallePedido() {
    }

    public DetallePedido(int cantidad, Factura factura, Menu menu) {
        this.cantidad = cantidad;
        this.factura = factura;
        this.menu = menu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
}