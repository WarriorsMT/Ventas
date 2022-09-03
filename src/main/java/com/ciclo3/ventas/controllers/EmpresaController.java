package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.services.EmpresaService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterprises")
public class EmpresaController {
    @Autowired
    EmpresaService servicio;

    @RequestMapping(method = RequestMethod.GET)
    public JSONObject listar() {
        return (JSONObject) JSONValue.parse(
                "{ " +
                        "\"ok\" : " + true + ", " +
                        "\"msg\" : \"Listado\", " +
                        "\"result\" : " + (List<EmpresaEntity>) this.servicio.listar() +
                        "}");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JSONObject buscarPorId(@PathVariable long id) {
        try {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Buscado\", " +
                            "\"result\" : " + this.servicio.buscarPorId(id) +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe la empresa\", " +
                            "}");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public JSONObject agregar(@RequestBody EmpresaEntity empresa) {
        if (this.servicio.buscarPorNombre(empresa.getNombre()) != null) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Ya existe el nombre\", " +
                            "}");
        } else if (this.servicio.buscarPorNit(empresa.getNit()) != null) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Ya existe el nit\", " +
                            "}");
        }
        try {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Agregado\", " +
                            "\"result\" : " + this.servicio.agregar(empresa) +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Revisar los datos\", " +
                            "}");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JSONObject borrar(@PathVariable long id) {
        if (this.servicio.findByIdEnEmpleado(id) != null) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Tiene empleados y no se puede eliminar\", " +
                            "}");
        }

        try {
            this.servicio.borrar(id);
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Eliminado\" " +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe la empresa\", " +
                            "}");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public JSONObject editar(@RequestBody EmpresaEntity nuevaEmpresa, @PathVariable long id) {
        try {
            this.servicio.buscarPorId(id);
            if (this.servicio.findByNombreEIdNoIgual(id, nuevaEmpresa.getNombre()) != null) {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"Ya existe el nombre\", " +
                                "}");
            } else if (this.servicio.findByNitEIdNoIgual(id, nuevaEmpresa.getNit()) != null) {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"Ya existe el nit\", " +
                                "}");
            }
            try {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + true + ", " +
                                "\"msg\" : \"Actualizado\", " +
                                "\"result\" : " + this.servicio.editar(nuevaEmpresa, id) +
                                "}");
            } catch (Exception e) {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"Revisar los datos\", " +
                                "}");
            }
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"No existe la empresa\", " +
                            "}");
        }
    }
}
