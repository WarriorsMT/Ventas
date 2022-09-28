package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.MovimientoDineroEntity;
import com.ciclo3.ventas.entities.RespuestaEntity;
import com.ciclo3.ventas.services.EmpleadoService;
import com.ciclo3.ventas.services.EmpresaService;
import com.ciclo3.ventas.services.MovimientoDineroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/enterprises/{id}")
public class MovimientoDineroController {
    @Autowired
    MovimientoDineroService servicio;

    @Autowired
    EmpresaService empresaServicio;

    @Autowired
    EmpleadoService empleadoServicio;

    @RequestMapping(value = "/movements", method = RequestMethod.GET)
    public List<MovimientoDineroEntity> listarPorEmpresa(@PathVariable long id) {
            return this.servicio.buscarPorEmpresa(id);
    }

    @RequestMapping(value = "/movements/{idm}", method = RequestMethod.GET)
    public MovimientoDineroEntity buscarPorId(@PathVariable long id, @PathVariable long idm) {
            return this.servicio.buscarPorId(idm);
    }

    @RequestMapping(value = "/movements", method = RequestMethod.POST)
    public MovimientoDineroEntity agregar(@RequestBody MovimientoDineroEntity movimiento, @PathVariable long id) {
        movimiento.getEmpresa().setId(id);
        movimiento.setFecha(new Date(System.currentTimeMillis()));
        return this.servicio.agregar(movimiento);
    }

    @RequestMapping(value = "/movements/{idm}", method = RequestMethod.DELETE)
    public boolean borrar(@PathVariable long id, @PathVariable long idm) {
        return this.servicio.borrar(idm);
    }

    @RequestMapping(value = "/movements/{idm}", method = RequestMethod.PATCH)
    public MovimientoDineroEntity editar(@RequestBody MovimientoDineroEntity nuevoMovimiento,
                                                  @PathVariable long id, @PathVariable long idm) {
        nuevoMovimiento.setFecha(new Date(System.currentTimeMillis()));
        return this.servicio.editar(nuevoMovimiento, idm);
    }

    @RequestMapping(value = "/movements/sum", method = RequestMethod.GET)
    public double totalPorEmpresa(@PathVariable long id) {
        return this.servicio.totalPorEmpresa(id);
    }
}
