package com.ciclo3.ventas.views.controllers;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.entities.MovimientoDineroEntity;
import com.ciclo3.ventas.util.Login;
import com.ciclo3.ventas.views.services.EmpleadoServiceView;
import com.ciclo3.ventas.views.services.EmpresaServiceView;
import com.ciclo3.ventas.views.services.MovimientoDineroServiceView;
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
@RequestMapping("/movimientos")
public class MovimientoDineroControllerView {

    @Autowired
    MovimientoDineroServiceView servicio;

    @Autowired
    EmpresaServiceView empresaServicio;

    @Autowired
    EmpleadoServiceView empleadoServicio;

    @GetMapping("/listar/{pagina}/{tamaño}")
    public String listar(@PathVariable int pagina, @PathVariable int tamaño,
                         Model modelo, HttpSession sesion) {
        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
        if (usuario != null) {
            Page<MovimientoDineroEntity> paginaActual = servicio.listarPagina(PageRequest.of(pagina - 1, tamaño), usuario);

            modelo.addAttribute("paginaActual", paginaActual);
            List<Integer> numerosPaginas = IntStream.rangeClosed(1, paginaActual.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            double total = servicio.totalPorEmpresa(usuario);
            modelo.addAttribute("totalPorEmpresa", total);
            modelo.addAttribute("numerosPaginas", numerosPaginas);
            modelo.addAttribute("usuario", sesion.getAttribute("usuario"));
            modelo.addAttribute("plantilla", "movimiento");
            modelo.addAttribute("movimientoeditado", new MovimientoDineroEntity());
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
        empleadoServicio.listar(usuario);
        MovimientoDineroEntity movimiento = new MovimientoDineroEntity();
        if (id > 0) {
            movimiento = servicio.buscarPorId(id, usuario);
        }
        modelo.addAttribute("usuario", sesion.getAttribute("usuario"));
        modelo.addAttribute("listausuarios", empleadoServicio.getLista());
        modelo.addAttribute("listaempresas", empresaServicio.getLista());
        modelo.addAttribute("movimientoeditado", movimiento);
        return "movimientoeditar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("movimientoeditado") MovimientoDineroEntity movimiento, HttpSession sesion) {
        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
        movimiento = servicio.guardar(movimiento, usuario);
        servicio.listar(usuario);
        int pagina = servicio.encontrarPagina(movimiento.getId(),
                InicioControllerView.TAMAÑO_PAGINA);

        String ruta = "redirect:/movimientos/listar/" + pagina + "/" + InicioControllerView.TAMAÑO_PAGINA;
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
        String ruta = "redirect:/movimientos/listar/" + pagina + "/" + InicioControllerView.TAMAÑO_PAGINA;
        return ruta;
    }
}
