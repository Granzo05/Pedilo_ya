package com.example.pedilo_ya.entities.Restaurante.Menu;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "costo")
    private double costo;
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinTable(
            name = "menu_ingredientes",
            joinColumns = @JoinColumn(name = "ingrediente_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private Menu menu;

    public Ingrediente() {
    }

    public Ingrediente(double costo, String nombre) {
        this.costo = costo;
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}