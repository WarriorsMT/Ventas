package com.ciclo3.ventas.entities;

import com.ciclo3.ventas.util.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "empleado")
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "correo", length = 100, unique = true, nullable = false)
    private String correo;

    @JoinColumn(name = "id_empresa", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private EmpresaEntity empresa;

    @Column(name = "rol", length = 18, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Rol rol;

    @Column(name = "password", columnDefinition="varchar(100) not null default crypt(now()::varchar, gen_salt('bf'))")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<MovimientoDineroEntity> transacciones;

    @Transient
    private String token;

    public EmpleadoEntity() {
    }

    public EmpleadoEntity(String nombre, String correo, EmpresaEntity empresa, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.empresa = empresa;
        this.rol = rol;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public EmpresaEntity getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<MovimientoDineroEntity> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<MovimientoDineroEntity> transacciones) {
        this.transacciones = transacciones;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
//    @Override
//    public String toString() {
//        return "{" +
//                "\"id\" : " + id + ", " +
//                "\"nombre\" : \"" + nombre + "\", " +
//                "\"correo\" : \"" + correo + "\", " +
//                "\"empresa\" : " + empresa + ", " +
//                "\"rol\" : \"" + rol + "\"" +
//                '}';
//    }
}
