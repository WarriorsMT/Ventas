package com.ciclo3.ventas.entities;

import javax.persistence.*;

enum Rol {
    ADMINISTRADOR,
    OPERATIVO;
}

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

    @JoinColumn(name = "id_empresa", nullable = false,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (id_empresa) REFERENCES empresa " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    @ManyToOne(fetch = FetchType.EAGER)
    private EmpresaEntity empresa;

    @Column(name = "rol", length = 13, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Rol rol;

    public EmpleadoEntity() {
    }

    public EmpleadoEntity(String nombre, String correo, EmpresaEntity empresa, Rol rol) {
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

    @Override
    public String toString() {
        return "{" +
                "\"id\" : " + id + ", " +
                "\"nombre\" : \"" + nombre + "\", " +
                "\"correo\" : \"" + correo + "\", " +
                "\"empresa\" : " + empresa + ", " +
                "\"rol\" : \"" + rol + "\"" +
                '}';
    }

}
