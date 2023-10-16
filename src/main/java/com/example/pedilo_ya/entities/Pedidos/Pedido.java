package com.example.pedilo_ya.entities.Pedidos;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipo_envio")
    private EnumTipoEnvio tipoEnvio;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private Restaurante restaurante;

    @OneToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detalles;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "telefono")
    private long telefono;

    public Pedido() {
    }

    //En caso que sea retiro en local no es necesario ni domicilio ni telefono del cliente
    public Pedido(EnumTipoEnvio tipoEnvio, Cliente cliente, Restaurante restaurante, Factura factura, List<DetallePedido> detalles) {
        this.tipoEnvio = tipoEnvio;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.factura = factura;
        this.detalles = detalles;
    }

    public Pedido(EnumTipoEnvio tipoEnvio, Cliente clienteId, Restaurante restauranteId, Factura facturaId, List<DetallePedido> detalleId, String domicilio, long telefono) {
        this.tipoEnvio = tipoEnvio;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.factura = factura;
        this.detalles = detalles;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(EnumTipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
}