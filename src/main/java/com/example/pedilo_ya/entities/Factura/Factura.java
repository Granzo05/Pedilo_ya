package com.example.pedilo_ya.entities.Factura;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Factura.DetalleFactura.DetalleFactura;
import com.example.pedilo_ya.entities.Factura.DetalleFactura.MetodoPago;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @OneToOne
    @JoinColumn(name = "id_detalle_factura")
    private DetalleFactura detalleFactura;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "email")
    private String email;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "telefono")
    private long telefono;

    @Column(name = "fecha_factura", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date registerDate;

    public Factura() {
    }

    public Factura(MetodoPago metodoPago, DetalleFactura detalleFactura, String email, String domicilio, long telefono) {
        this.metodoPago = metodoPago;
        this.detalleFactura = detalleFactura;
        this.email = email;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}