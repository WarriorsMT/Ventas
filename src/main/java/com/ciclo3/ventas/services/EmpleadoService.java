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
//        return this.repositorio.findAll();
        return this.repositorio.findAllByOrderByNombreAsc();
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

    public List<EmpleadoEntity> buscarPorEmpresa(Long id_empresa) {
        return this.repositorio.findByEmpresa(id_empresa);
    }

    public EmpleadoEntity findByIdEnMovimiento(Long id) {
        return this.repositorio.findByIdEnMovimiento(id);
    }

    public EmpleadoEntity agregar(EmpleadoEntity empleado) {
        return repositorio.save(empleado);
    }

    public boolean borrar(long id) {
        try {
            repositorio.deleteById(id);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public EmpleadoEntity editar(EmpleadoEntity nuevoEmpleado, long id) {
        return repositorio.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevoEmpleado.getNombre());
                    empleado.setCorreo(nuevoEmpleado.getCorreo());
                    empleado.setEmpresa(nuevoEmpleado.getEmpresa());
                    empleado.setRol(nuevoEmpleado.getRol());
                    return repositorio.save(empleado);
                }).get();
    }

    public int savePassword(EmpleadoEntity empleado, long id) {
        return repositorio.savePassword(id, empleado.getPassword());
    }

    public String generatePassword(String password) {
        return repositorio.generatePassword(password);
    }

    public EmpleadoEntity Login(String correo, String clave) {
        return repositorio.Login(correo, clave);
    }
}
