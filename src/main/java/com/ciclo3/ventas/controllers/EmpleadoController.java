package com.ciclo3.ventas.controllers;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.entities.RespuestaEntity;
import com.ciclo3.ventas.security.FiltroSecurity;
import com.ciclo3.ventas.services.EmpleadoService;
import com.ciclo3.ventas.services.EmpresaService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class EmpleadoController {
    @Autowired
    EmpleadoService servicio;

    @Autowired
    EmpresaService empresaServicio;

    @RequestMapping(method = RequestMethod.GET)
    public List<EmpleadoEntity> listar() {
        return this.servicio.listar();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EmpleadoEntity buscarPorId(@PathVariable long id) {
        return this.servicio.buscarPorId(id);
    }

    @RequestMapping(value = "/enterprise/{id_empresa}", method = RequestMethod.GET)
    public List<EmpleadoEntity> buscarPorEmpresa(@PathVariable long id_empresa) {
        return this.servicio.buscarPorEmpresa(id_empresa);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RespuestaEntity agregar(@RequestBody EmpleadoEntity empleado) {
        try {
            this.empresaServicio.buscarPorId(empleado.getEmpresa().getId());
        } catch (Exception e) {
            return new RespuestaEntity("Agregar Empleado",
                            "Ya existe la empresa",
                            false);
        }
        if (this.servicio.buscarPorCorreo(empleado.getCorreo()) != null) {
            return new RespuestaEntity("Agregar Empleado",
                            "Ya existe el correo",
                            false);
        }
        try {
            //empleado.setPassword(this.servicio.generatePassword(""+System.currentTimeMillis()));
            empleado.setPassword(this.servicio.generatePassword(empleado.getCorreo()));
            return new RespuestaEntity("Agregar Empleado",
                            this.servicio.agregar(empleado),
                            true);
        } catch (Exception e) {
            return new RespuestaEntity("Agregar Empleado",
                            "Revisar los datos",
                            false);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean borrar(@PathVariable long id) {
        return this.servicio.borrar(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public RespuestaEntity editar(@RequestBody EmpleadoEntity nuevaEmpleado, @PathVariable long id) {
        try {
            this.empresaServicio.buscarPorId(nuevaEmpleado.getEmpresa().getId());
        } catch (Exception e) {
            return new RespuestaEntity("Editar Empleado",
                            "No existe la empresa",
                            false);
        }
        try {
            this.servicio.buscarPorId(id);
            if (this.servicio.findByCorreoEIdNoIgual(id, nuevaEmpleado.getCorreo()) != null) {
                return new RespuestaEntity("Editar Empleado",
                                "Ya existe el correo",
                                false);
            }
            try {
                return new RespuestaEntity("Editar Empleado",
                                this.servicio.editar(nuevaEmpleado, id),
                                true);
            } catch (Exception e) {
                return new RespuestaEntity("Editar Empresa",
                                "Revisar los datos",
                                false);
            }
        } catch (Exception e) {
            return new RespuestaEntity("Editar Empleado",
                            "No existe",
                            false);
        }
    }

    @RequestMapping(value = "/password/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<RespuestaEntity> savePassword(@RequestBody EmpleadoEntity empleado, @PathVariable long id) {
        if (this.servicio.savePassword(empleado, id) != 0) {
            return new ResponseEntity<>(
                    new RespuestaEntity("Editar Empleado",
                            "Contraseña cambiada",
                            true),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new RespuestaEntity("Editar Empleado",
                            "No existe",
                            false),
                    HttpStatus.OK);
        }
    }

    @GetMapping("/login")
    public EmpleadoEntity login(@RequestParam("usuario") String correo, @RequestParam("clave") String clave) {
        EmpleadoEntity usuario = this.servicio.Login(correo, clave);
        if (usuario != null) {
            usuario.setToken(generarToken(correo, usuario.getRol().toString()));
            return usuario;
        }
        return null;
    }

    private String generarToken(String correo, String rol) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(rol);

        String token = Jwts
                .builder()
                .setId("MisionTIC - Ciclo3")
                .setSubject(correo)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        FiltroSecurity.SECRETO.getBytes())
                .compact();

        return "Bearer " + token;
    }

}
