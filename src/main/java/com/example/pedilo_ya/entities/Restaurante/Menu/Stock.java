package com.example.pedilo_ya.entities.Restaurante.Menu;

import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "stock")
public class Stock {
    @Column(name = "fecha_llegada", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date fechaLlegada;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "medida")
    private String medida;
    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private Restaurante restaurante;
    @OneToOne
    private Ingrediente ingrediente;

    public Stock() {
    }

    public Stock(int cantidad, String medida, Restaurante restaurante, Ingrediente ingrediente) {
        this.cantidad = cantidad;
        this.medida = medida;
        this.restaurante = restaurante;
        this.ingrediente = ingrediente;
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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }
}
