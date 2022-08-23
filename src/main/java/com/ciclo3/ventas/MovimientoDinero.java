package com.ciclo3.ventas;

import java.util.Date;

public class MovimientoDinero {

    private String concepto;
    private double monto;
    private Empleado usuario;
    private Empresa empresa;
    private Date fecha;

    public MovimientoDinero(String concepto, double monto, Empleado usuario, Empresa empresa, Date fecha) {
        this.concepto = concepto;
        this.monto = monto;
        this.usuario = usuario;
        this.empresa = empresa;
        this.fecha = fecha;
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

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "MovimientoDinero{" + "concepto=" + concepto + ", monto=" + monto + ", usuario=" + usuario + ", empresa=" + empresa + ", fecha=" + fecha + '}';
    }

}
