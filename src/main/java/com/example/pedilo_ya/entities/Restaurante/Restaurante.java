package com.example.pedilo_ya.entities.Restaurante;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "contraseña")
    private String contraseña;
    @Column(name = "telefono")
    private long telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "tipo_comida")
    private String tipoDeComida;
    @Column(name = "fecha_registracion", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaRegistracion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    public Restaurante() {
    }

    public Restaurante(String domicilio, long telefono) {
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoDeComida() {
        return tipoDeComida;
    }

    public void setTipoDeComida(String tipoDeComida) {
        this.tipoDeComida = tipoDeComida;
    }

    public Long getId() {
        return id;
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

    public Date getFechaRegistracion() {
        return fechaRegistracion;
    }

    public void setFechaRegistracion(Date fechaRegistracion) {
        this.fechaRegistracion = fechaRegistracion;
    }
}