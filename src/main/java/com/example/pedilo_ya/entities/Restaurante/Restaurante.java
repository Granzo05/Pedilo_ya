package com.example.pedilo_ya.entities.Restaurante;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "telefono")
    private long telefono;
    @Column(name = "fecha_registracion", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaRegistracion;

    public Restaurante() {
    }
    public Restaurante(String domicilio, long telefono) {
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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