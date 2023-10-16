package com.example.pedilo_ya.entities.Restaurante.Menu;

import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "medida")
    private String medida;
    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private long idRestaurante;
    @Column(name = "id_ingrediente")
    private Ingrediente id_ingrediente;

    @Column(name = "fecha_llegada", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaLlegada;

    public Stock() {
    }

    public Stock(int cantidad, String medida, Restaurante restaurante, Ingrediente id_ingrediente) {
        this.cantidad = cantidad;
        this.medida = medida;
        this.idRestaurante = restaurante.getId();
        this.id_ingrediente = id_ingrediente;
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

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Ingrediente getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(Ingrediente id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }
}
