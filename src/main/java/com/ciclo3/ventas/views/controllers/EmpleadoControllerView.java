package com.ciclo3.ventas.views.controllers;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.entities.RespuestaEntity;
import com.ciclo3.ventas.util.Login;
import com.ciclo3.ventas.views.services.EmpleadoServiceView;
import com.ciclo3.ventas.views.services.EmpresaServiceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/empleados")
public class EmpleadoControllerView {

    @Autowired
    EmpleadoServiceView servicio;

    @Autowired
    EmpresaServiceView empresaServicio;

    @GetMapping("/listar/{pagina}/{tamaño}")
    public String listar(@PathVariable int pagina, @PathVariable int tamaño,
                         Model modelo, HttpSession sesion) {
        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
        if (usuario != null) {
            Page<EmpleadoEntity> paginaActual = servicio.listarPagina(PageRequest.of(pagina - 1, tamaño), usuario);

            modelo.addAttribute("paginaActual", paginaActual);
            List<Integer> numerosPaginas = IntStream.rangeClosed(1, paginaActual.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            modelo.addAttribute("numerosPaginas", numerosPaginas);
            modelo.addAttribute("usuario", sesion.getAttribute("usuario"));
            modelo.addAttribute("plantilla", "empleado");
            modelo.addAttribute("empleadoeditado", new EmpleadoEntity());
            sesion.setAttribute("numeroPaginaActual", paginaActual.getNumber() + 1);
        }else{
            modelo.addAttribute("plantilla", "presentacion");
            modelo.addAttribute("mensajeerror", "usuario no logueado");
        }
        modelo.addAttribute("menu", InicioControllerView.generarMenu());
        modelo.addAttribute("login", new Login());
        return "inicio";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model modelo, HttpSession sesion) {
        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
        empresaServicio.listar(usuario);
        EmpleadoEntity empleado = new EmpleadoEntity();
        if (id > 0) {
            empleado = servicio.buscarPorId(id, usuario);
        }
        modelo.addAttribute("usuario", sesion.getAttribute("usuario"));
        modelo.addAttribute("listaroles", servicio.listarRoles());
        modelo.addAttribute("listaempresas", empresaServicio.getLista());
        modelo.addAttribute("empleadoeditado", empleado);
        return "empleadoeditar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("empleadoeditado") EmpleadoEntity empleado, HttpSession sesion,
                          RedirectAttributes redirectAttributes) {
        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
        int pagina = 1;
        RespuestaEntity respuesta = servicio.guardar(empleado, usuario);
        if (respuesta.getOk()) {
            if (respuesta.getResult() instanceof EmpleadoEntity) {
                empleado = (EmpleadoEntity) respuesta.getResult();
                pagina = servicio.encontrarPagina(empleado.getId(),
                        InicioControllerView.TAMAÑO_PAGINA);
            }
        } else {
            redirectAttributes.addFlashAttribute("mensajeerror", (String) respuesta.getResult());
        }
        String ruta = "redirect:/empleados/listar/" + pagina + "/" + InicioControllerView.TAMAÑO_PAGINA;
        return ruta;
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable long id, Model modelo, HttpSession sesion,
                         RedirectAttributes redirectAttributes) {
        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
        if (servicio.borrar(id, usuario)) {
            servicio.listar(usuario);
        } else {
            redirectAttributes.addFlashAttribute("mensajeerror", "No se pudo eliminar la Empresa");
        }
        int pagina = (int) sesion.getAttribute("numeroPaginaActual");
        String ruta = "redirect:/empleados/listar/" + pagina + "/" + InicioControllerView.TAMAÑO_PAGINA;
        return ruta;
    }
}
