package com.ciclo3.ventas;

import java.util.ArrayList;
import java.util.Date;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class VentasApplication {

	public static void main(String[] args) {
		//SpringApplication.run(VentasApplication.class, args);
        Empresa empresa = new Empresa("Empresa ABC S.A.", "Calle 120 ...", "6000000000", "123456789");
        System.out.println(empresa);
        empresa.setNombre("MisiónTIC");
        empresa.setDireccion("Avenida Santander ...");
        empresa.setTelefono("6060606060");
        empresa.setNit("987654321");
        System.out.println(empresa.getNombre()
                + " - " + empresa.getDireccion()
                + " - " + empresa.getTelefono()
                + " - " + empresa.getNit());

        System.out.println("");
        ArrayList<Empleado> emp = new ArrayList<>();
        emp.add(new Empleado("María", "maria@email.com", empresa, Rol.OPERATIVO));
        emp.add(new Empleado("Juan", "juan@email.com", empresa, Rol.OPERATIVO));
        emp.add(new Empleado("Andrea", "andrea@email.com", empresa, Rol.OPERATIVO));
        emp.add(new Empleado("Pedro", "pedro@email.com", empresa, Rol.OPERATIVO));
        for (Empleado empleado : emp) {
            System.out.println(empleado);
        }
        emp.get(0).setNombre("María de los Angeles");
        emp.get(0).setCorreo("mariaangeles@email.com");
        emp.get(0).setRol(Rol.ADMINISTRADOR);

        System.out.println("");
        for (Empleado empleado : emp) {
            System.out.println(empleado.getNombre()
                    + " * " + empleado.getCorreo()
                    + " * " + empleado.getEmpresa()
                    + " * " + empleado.getRol());
        }

        System.out.println("");
        ArrayList<MovimientoDinero> dinero = new ArrayList<>();
        dinero.add(new MovimientoDinero("Ingreso 1 ...", 2000000, emp.get(2), empresa, new Date()));
        dinero.add(new MovimientoDinero("Ingreso 2 ...", 100000, emp.get(2), empresa, new Date()));
        dinero.add(new MovimientoDinero("Egreso 2 ...", -200000, emp.get(2), empresa, new Date()));
        for (MovimientoDinero movimiento : dinero) {
            System.out.println(movimiento);
        }
        dinero.get(1).setConcepto("Venta 2 ...");
        dinero.get(1).setMonto(50000);
        dinero.get(1).setUsuario(emp.get(3));
        dinero.get(1).setFecha(new Date());
        
        System.out.println("");
        double total = 0;
        for (MovimientoDinero movimiento : dinero) {
            System.out.println(movimiento.getConcepto()
                    + " * " + movimiento.getMonto()
                    + " * " + movimiento.getUsuario()
                    + " * " + movimiento.getEmpresa()
                    + " * " + movimiento.getFecha());
            total += movimiento.getMonto();
        }
        System.out.println(total);
	}

}
