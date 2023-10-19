package com.example.pedilo_ya.entities.Factura;

import com.example.pedilo_ya.entities.Cliente.Cliente;
import com.example.pedilo_ya.entities.Factura.DetalleFactura.DetalleFactura;
import com.example.pedilo_ya.entities.Factura.DetalleFactura.MetodoPago;
import com.example.pedilo_ya.entities.Factura.DetalleFactura.TipoFactura;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura {
    @Column(name = "fecha_factura", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fecha;
    @Column(name = "tipoFactura")
    private TipoFactura tipoFactura;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "factura")
    private List<DetalleFactura> detallesFactura;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @Column(name = "email")
    private String email;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "telefono")
    private long telefono;

    public Factura() {
    }

    public Factura(MetodoPago metodoPago, String email, String domicilio, long telefono) {
        this.metodoPago = metodoPago;
        this.email = email;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public TipoFactura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(TipoFactura tipoFactura) {
        this.tipoFactura = tipoFactura;
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

    public List<DetalleFactura> getDetallesFactura() {
        return detallesFactura;
    }

    public void setDetallesFactura(List<DetalleFactura> detallesFactura) {
        this.detallesFactura = detallesFactura;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}