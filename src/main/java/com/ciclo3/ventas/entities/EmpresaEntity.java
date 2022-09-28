package com.ciclo3.ventas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "telefono", length = 100, nullable = false)
    private String telefono;

    @Column(name = "nit", length = 100, unique = true, nullable = false)
    private String nit;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa")
    private List<EmpleadoEntity> usuarios;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa")
    private List<MovimientoDineroEntity> transacciones;

    public EmpresaEntity() {
    }

    public EmpresaEntity(long id, String nombre, String direccion, String telefono, String nit) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<EmpleadoEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<EmpleadoEntity> usuarios) {
        this.usuarios = usuarios;
    }

    public List<MovimientoDineroEntity> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<MovimientoDineroEntity> transacciones) {
        this.transacciones = transacciones;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"id\" : " + id + ", " +
//                "\"nombre\" : \"" + nombre + "\", " +
//                "\"direccion\" : \"" + direccion + "\", " +
//                "\"telefono\" : \"" + telefono + "\", " +
//                "\"nit\" : \"" + nit + "\"" +
//                '}';
//    }
}
