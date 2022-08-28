package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public EmpresaEntity buscar(@PathVariable long id) {
        return this.servicio.buscar(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public EmpresaEntity agregar(@RequestBody EmpresaEntity empresa) {
        return this.servicio.agregar(empresa);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void borrar(@PathVariable long id) {
        this.servicio.borrar(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public EmpresaEntity editar(@RequestBody EmpresaEntity nuevaEmpresa, @PathVariable long id) {
        return this.servicio.editar(nuevaEmpresa, id);
    }
}
