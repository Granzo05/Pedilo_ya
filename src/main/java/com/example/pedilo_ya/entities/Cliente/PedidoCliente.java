package com.example.pedilo_ya.entities.Cliente;

import com.example.pedilo_ya.entities.Pedidos.EnumTipoEnvio;
import com.example.pedilo_ya.entities.Restaurante.Menu.Menu;

import java.util.Date;
import java.util.List;

public class PedidoCliente {
    private List<Menu> menus;
    private String email;
    private Date fecha;
    private EnumTipoEnvio tipoEnvio;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EnumTipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(EnumTipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
