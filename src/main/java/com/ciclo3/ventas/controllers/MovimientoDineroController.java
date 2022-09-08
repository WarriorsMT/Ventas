package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.MovimientoDineroEntity;
import com.ciclo3.ventas.services.EmpleadoService;
import com.ciclo3.ventas.services.EmpresaService;
import com.ciclo3.ventas.services.MovimientoDineroService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/movements",method = RequestMethod.GET)
    public JSONObject listarPorEmpresa(@PathVariable long id) {
        return (JSONObject) JSONValue.parse(
                "{ " +
                        "\"ok\" : " + true + ", " +
                        "\"msg\" : \"Listado\", " +
                        "\"result\" : " + (List<MovimientoDineroEntity>) this.servicio.buscarPorEmpresa(id) +
                        "}");
    }

    @RequestMapping(value = "/movements/{idm}", method = RequestMethod.GET)
    public JSONObject buscarPorId(@PathVariable long id, @PathVariable long idm) {
        try {
            if(this.servicio.buscarPorId(idm).getEmpresa().getId()!=id){
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"El movimiento no pertenence a la empresa\" " +
                                "}");
            }
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Buscado\", " +
                            "\"result\" : " + this.servicio.buscarPorId(idm) +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe el movimiento\" " +
                            "}");
        }
    }

    @RequestMapping(value = "/movements", method = RequestMethod.POST)
    public JSONObject agregar(@RequestBody MovimientoDineroEntity movimiento, @PathVariable long id) {
        try {
            this.empresaServicio.buscarPorId(id);
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe la empresa\" " +
                            "}");
        }

        try {
            if(this.empleadoServicio.buscarPorId(movimiento.getUsuario().getId()).getEmpresa().getId()!=id){
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"El usuario no pertenece a la empresa\" " +
                                "}");
            }
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe el usuario\" " +
                            "}");
        }

        try {
            movimiento.getEmpresa().setId(id);
            movimiento.setFecha(new Date(System.currentTimeMillis()));
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Agregado\", " +
                            "\"result\" : " + this.servicio.agregar(movimiento) +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Revisar los datos\" " +
                            "}");
        }
    }

    @RequestMapping(value = "/movements/{idm}", method = RequestMethod.DELETE)
    public JSONObject borrar(@PathVariable long id, @PathVariable long idm) {
        try {
            this.empresaServicio.buscarPorId(id);
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe la empresa\" " +
                            "}");
        }

        try {
            if(this.servicio.buscarPorId(idm).getEmpresa().getId()!=id){
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"El movimiento no pertenece a la empresa\" " +
                                "}");
            }

            this.servicio.borrar(idm);
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Eliminado\" " +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe el movimiento\" " +
                            "}");
        }
    }

    @RequestMapping(value = "/movements/{idm}", method = RequestMethod.PATCH)
    public JSONObject editar(@RequestBody MovimientoDineroEntity nuevoMovimiento,
                             @PathVariable long id, @PathVariable long idm) {
        try {
            this.empresaServicio.buscarPorId(id);
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe la empresa\" " +
                            "}");
        }

        try {
            if(this.empleadoServicio.buscarPorId(nuevoMovimiento.getUsuario().getId()).getEmpresa().getId()!=id){
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"El usuario no pertenece a la empresa\" " +
                                "}");
            }
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe el usuario\" " +
                            "}");
        }

        try {
            this.servicio.buscarPorId(idm);
            try {
                nuevoMovimiento.getEmpresa().setId(id);
            } catch (Exception e) {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"Revisar los datos de empresa\" " +
                                "}");
            }
            nuevoMovimiento.setFecha(new Date(System.currentTimeMillis()));
            try {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + true + ", " +
                                "\"msg\" : \"Actualizado\", " +
                                "\"result\" : " + this.servicio.editar(nuevoMovimiento, idm) +
                                "}");
            } catch (Exception e) {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"Revisar los datos\" " +
                                "}");
            }
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe el movimiento\" " +
                            "}");
        }
    }
}
