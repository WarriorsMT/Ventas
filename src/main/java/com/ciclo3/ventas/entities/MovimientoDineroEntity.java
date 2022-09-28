package com.ciclo3.ventas.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "movimiento")
public class MovimientoDineroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "concepto", length = 100, nullable = false)
    private String concepto;

    @Column(name = "monto", nullable = false)
    private double monto;

    @JoinColumn(name = "id_empleado", nullable = false,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (id_empleado) REFERENCES empleado(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    @ManyToOne(fetch = FetchType.EAGER)
    private EmpleadoEntity usuario;

    @JoinColumn(name = "id_empresa", nullable = false,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (id_empresa) REFERENCES empresa(id) " +
                            "ON UPDATE CASCADE " +
                            "ON DELETE RESTRICT"
            )
    )
    @ManyToOne(fetch = FetchType.EAGER)
    private EmpresaEntity empresa;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    public MovimientoDineroEntity() {
    }

    public MovimientoDineroEntity(String concepto, double monto, EmpleadoEntity usuario, EmpresaEntity empresa, Date fecha) {
        this.concepto = concepto;
        this.monto = monto;
        this.usuario = usuario;
        this.empresa = empresa;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public EmpleadoEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(EmpleadoEntity usuario) {
        this.usuario = usuario;
    }

    public EmpresaEntity getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"id\" : " + id + ", " +
//                "\"concepto\" : \"" + concepto + "\", " +
//                "\"monto\" : \"" + monto + "\", " +
//                "\"usuario\" : " + usuario + ", " +
//                "\"empresa\" : " + empresa + ", " +
//                "\"fecha\" : \"" + fecha + "\"" +
//                '}';
//    }
}
