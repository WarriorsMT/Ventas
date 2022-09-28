package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.entities.RespuestaEntity;
import com.ciclo3.ventas.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterprises")
public class EmpresaController {
    @Autowired
    EmpresaService servicio;

    @RequestMapping(method = RequestMethod.GET)
    public List<EmpresaEntity> listar() {
        return this.servicio.listar();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EmpresaEntity buscarPorId(@PathVariable long id) {
        return this.servicio.buscarPorId(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RespuestaEntity agregar(@RequestBody EmpresaEntity empresa) {
        if (this.servicio.buscarPorNombre(empresa.getNombre()) != null) {
            return new RespuestaEntity("Agregar Empresa",
                            "Ya existe el nombre",
                            false);
        } else if (this.servicio.buscarPorNit(empresa.getNit()) != null) {
            return new RespuestaEntity("Agregar Empresa",
                            "Ya existe el nit",
                            false);
        }
        try {
            return new RespuestaEntity("Agregar Empresa",
                            this.servicio.agregar(empresa),
                            true);
        } catch (Exception e) {
            return new RespuestaEntity("Agregar Empresa",
                            "Revisar los datos",
                            false);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean borrar(@PathVariable long id) {
        return this.servicio.borrar(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public RespuestaEntity editar(@RequestBody EmpresaEntity nuevaEmpresa, @PathVariable long id) {
        try {
            this.servicio.buscarPorId(id);
            if (this.servicio.findByNombreEIdNoIgual(id, nuevaEmpresa.getNombre()) != null) {
                return new RespuestaEntity("Editar Empresa",
                                "Ya existe el nombre",
                                false);
            } else if (this.servicio.findByNitEIdNoIgual(id, nuevaEmpresa.getNit()) != null) {
                return new RespuestaEntity("Editar Empresa",
                                "Ya existe el nit",
                                false);
            }
            try {
                return new RespuestaEntity("Editar Empresa",
                                this.servicio.editar(nuevaEmpresa, id),
                                true);
            } catch (Exception e) {
                return new RespuestaEntity("Editar Empresa",
                                "Revisar los datos",
                                false);
            }
        } catch (Exception e) {
            return new RespuestaEntity("Editar Empresa",
                            "No existe",
                            false);
        }
    }
}
