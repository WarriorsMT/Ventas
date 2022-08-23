package com.ciclo3.ventas;

enum Rol {
    ADMINISTRADOR, OPERATIVO;
}

public class Empleado {

    private String nombre;
    private String correo;
    private Empresa empresa;
    private Rol rol;

    public Empleado(String nombre, String correo, Empresa empresa, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.empresa = empresa;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Empleado{" + "nombre=" + nombre + ", correo=" + correo + ", empresa=" + empresa + ", rol=" + rol + '}';
    }
     
}
