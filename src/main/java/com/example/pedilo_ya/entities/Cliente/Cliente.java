package com.example.pedilo_ya.entities.Cliente;

import com.example.pedilo_ya.entities.Factura.Factura;
import com.example.pedilo_ya.entities.Pedidos.Pedido;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Column(name = "fecha_registro", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaRegistro;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "email")
    private String email;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "telefono")
    private long telefono;
    @Column(name = "contraseña")
    private String contraseña;
    @OneToOne(mappedBy = "cliente")
    private Factura factura;
    // Privilegios de clientes <1>
    @Column(name = "privilegio")
    private String privilegios;
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String email, String domicilio, long telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public String getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(String privilegios) {
        this.privilegios = privilegios;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
