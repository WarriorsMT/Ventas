package com.ciclo3.ventas.services;

import com.ciclo3.ventas.entities.MovimientoDineroEntity;
import com.ciclo3.ventas.repositories.MovimientoDineroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoDineroService {
    @Autowired
    MovimientoDineroRepository repositorio;

    public List<MovimientoDineroEntity> buscarPorEmpresa(long id_empresa) {
        return this.repositorio.findByEmpresa(id_empresa);
    }

    public MovimientoDineroEntity buscarPorId(long id) {
        return this.repositorio.findById(id).get();
    }

    public MovimientoDineroEntity agregar(MovimientoDineroEntity movimiento) {
        return repositorio.save(movimiento);
    }

    public boolean borrar(long id) {
        try {
            repositorio.deleteById(id);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public MovimientoDineroEntity editar(MovimientoDineroEntity nuevoMovimiento, long id) {
        return  repositorio.findById(id)
                .map(movimiento -> {
                    movimiento.setConcepto(nuevoMovimiento.getConcepto());
                    movimiento.setMonto(nuevoMovimiento.getMonto());
                    movimiento.setUsuario(nuevoMovimiento.getUsuario());
                    movimiento.setEmpresa(nuevoMovimiento.getEmpresa());
                    movimiento.setFecha(nuevoMovimiento.getFecha());
                    return repositorio.save(movimiento);
                }).get();
    }

    public double totalPorEmpresa(long id_empresa) {
        return this.repositorio.totalPorEmpresa(id_empresa);
    }
}
