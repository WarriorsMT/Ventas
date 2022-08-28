package com.ciclo3.ventas.services;

import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    EmpresaRepository repositorio;

    public List<EmpresaEntity> listar() {
        return this.repositorio.findAll();
    }

    public EmpresaEntity buscar(long id) {
        return this.repositorio.findById(id).get();
    }

    public EmpresaEntity agregar(EmpresaEntity empresa) {
        return repositorio.save(empresa);
    }

    public void borrar(long id) {
        repositorio.deleteById(id);
    }

    public EmpresaEntity editar(EmpresaEntity nuevoEmpresa, long id) {
        return  repositorio.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevoEmpresa.getNombre());
                    empleado.setDireccion(nuevoEmpresa.getDireccion());
                    empleado.setTelefono(nuevoEmpresa.getTelefono());
                    empleado.setNit(nuevoEmpresa.getNit());
                    return repositorio.save(empleado);
                }).get();
    }
}
