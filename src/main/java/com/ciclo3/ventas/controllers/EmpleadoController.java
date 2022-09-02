package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.services.EmpleadoService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class EmpleadoController {
    @Autowired
    EmpleadoService servicio;

    @RequestMapping(method = RequestMethod.GET)
    public JSONObject listar() {
        return (JSONObject) JSONValue.parse(
                "{ " +
                        "\"ok\" : " + true + ", " +
                        "\"msg\" : \"Listado\", " +
                        "\"result\" : " + (List< EmpleadoEntity>) this.servicio.listar() +
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
                            "\"msg\" : \"No existe\", " +
                            "}");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public JSONObject agregar(@RequestBody EmpleadoEntity empleado) {
        if (this.servicio.buscarPorCorreo(empleado.getCorreo()) != null) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Ya existe el correo\", " +
                            "}");
        }
        try {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + true + ", " +
                            "\"msg\" : \"Agregado\", " +
                            "\"result\" : " + this.servicio.agregar(empleado) +
                            "}");
        } catch (Exception e) {
            return (JSONObject) JSONValue.parse(
                    "{ " +
                            "\"ok\" : " + false + ", " +
                            "\"msg\" : \"Revisar los datos  \", " +
                            "}");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JSONObject borrar(@PathVariable long id) {
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
                            "\"msg\" : \"No existe\", " +
                            "}");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public JSONObject editar(@RequestBody EmpleadoEntity nuevaEmpleado, @PathVariable long id) {
        try {
            this.servicio.buscarPorId(id);
            if (this.servicio.findByCorreoEIdNoIgual(id, nuevaEmpleado.getCorreo()) != null) {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + false + ", " +
                                "\"msg\" : \"Ya existe el correo\", " +
                                "}");
            }
            try {
                return (JSONObject) JSONValue.parse(
                        "{ " +
                                "\"ok\" : " + true + ", " +
                                "\"msg\" : \"Actualizado\", " +
                                "\"result\" : " + this.servicio.editar(nuevaEmpleado, id) +
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
                            "\"msg\" : \"No existe\", " +
                            "}");
        }
    }
}
