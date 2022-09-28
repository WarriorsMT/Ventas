package com.ciclo3.ventas.util;

import java.util.List;

public class ItemMenu {
    private String texto;
    private String url;
    private String icono;
    private Rol []rol;

    private List<ItemMenu> hijos;

    public ItemMenu(String texto, String url, String icono, Rol []rol) {
        this.texto = texto;
        this.url = url;
        this.icono = icono;
        this.rol = rol;
        this.hijos = null;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Rol[] getRol() {
        return rol;
    }

    public void setRol(Rol[] rol) {
        this.rol = rol;
    }

    public List<ItemMenu> getHijos() {
        return hijos;
    }

    public void setHijos(List<ItemMenu> hijos) {
        this.hijos = hijos;
    }
}
