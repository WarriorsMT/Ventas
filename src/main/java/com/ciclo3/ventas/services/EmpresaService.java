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

    public EmpresaEntity buscarPorId(long id) {
        return this.repositorio.findById(id).get();
    }

    public EmpresaEntity buscarPorNombre(String nombre) {
        return this.repositorio.findByNombre(nombre);
    }

    public EmpresaEntity buscarPorNit(String nit) {
        return this.repositorio.findByNit(nit);
    }

    public EmpresaEntity findByNombreEIdNoIgual(Long id, String nombre) {
        return this.repositorio.findByNombreEIdNoIgual(id, nombre);
    }

    public EmpresaEntity findByNitEIdNoIgual(Long id, String nit) {
        return this.repositorio.findByNitEIdNoIgual(id, nit);
    }

    public EmpresaEntity findByIdEnEmpleado(Long id) {
        return this.repositorio.findByIdEnEmpleado(id);
    }

    public EmpresaEntity agregar(EmpresaEntity empresa) {
        return repositorio.save(empresa);
    }

    public void borrar(long id) {
        repositorio.deleteById(id);
    }

    public EmpresaEntity editar(EmpresaEntity nuevoEmpresa, long id) {
        return  repositorio.findById(id)
                .map(empresa -> {
                    empresa.setNombre(nuevoEmpresa.getNombre());
                    empresa.setDireccion(nuevoEmpresa.getDireccion());
                    empresa.setTelefono(nuevoEmpresa.getTelefono());
                    empresa.setNit(nuevoEmpresa.getNit());
                    return repositorio.save(empresa);
                }).get();
    }
}
