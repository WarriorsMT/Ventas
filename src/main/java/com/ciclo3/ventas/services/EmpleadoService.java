package com.ciclo3.ventas.services;


import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository repositorio;

    public List<EmpleadoEntity> listar() {
        return this.repositorio.findAll();
    }

    public EmpleadoEntity buscarPorId(long id) {
        return this.repositorio.findById(id).get();
    }

    public EmpleadoEntity buscarPorCorreo(String correo) {
        return this.repositorio.findByCorreo(correo);
    }

    public EmpleadoEntity findByCorreoEIdNoIgual(Long id, String correo) {
        return this.repositorio.findByCorreoEIdNoIgual(id, correo);
    }

    public EmpleadoEntity agregar(EmpleadoEntity empleado) {
        return repositorio.save(empleado);
    }

    public void borrar(long id) {
        repositorio.deleteById(id);
    }

    public EmpleadoEntity editar(EmpleadoEntity nuevoEmpleado, long id) {
        return  repositorio.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevoEmpleado.getNombre());
                    empleado.setCorreo(nuevoEmpleado.getCorreo());
                    empleado.setEmpresa(nuevoEmpleado.getEmpresa());
                    empleado.setRol(nuevoEmpleado.getRol());
                    return repositorio.save(empleado);
                }).get();
    }
}
