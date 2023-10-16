package com.example.pedilo_ya.entities.Restaurante.Menu;

import com.example.pedilo_ya.entities.Restaurante.Restaurante;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tiempo")
    private int tiempoCoccion;
    @Column(name = "tipo")
    private EnumTipoMenu tipo;
    @Column(name = "comensales")
    private int comensales;
    @Column(name = "precio")
    private double precio;

    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private Restaurante restaurante;
    @OneToMany
    @JoinTable(
            name = "menu_ingredientes",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientes = new ArrayList<>();

    public Menu() {
    }

    public Menu(int tiempoCoccion, EnumTipoMenu tipo, int comensales, double precio, List<Ingrediente> ingredientes) {
        this.tiempoCoccion = tiempoCoccion;
        this.tipo = tipo;
        this.comensales = comensales;
        this.precio = precio;
        this.ingredientes = ingredientes;
    }

    public Menu(int tiempoCoccion, EnumTipoMenu tipo, int comensales, double precio) {
        this.tiempoCoccion = tiempoCoccion;
        this.tipo = tipo;
        this.comensales = comensales;
        this.precio = precio;
    }


    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTiempoCoccion() {
        return tiempoCoccion;
    }

    public void setTiempoCoccion(int tiempoCoccion) {
        this.tiempoCoccion = tiempoCoccion;
    }

    public EnumTipoMenu getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoMenu tipo) {
        this.tipo = tipo;
    }

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void addIngredientes(Ingrediente ingredientes) {
        this.ingredientes.add(ingredientes);
    }
}
