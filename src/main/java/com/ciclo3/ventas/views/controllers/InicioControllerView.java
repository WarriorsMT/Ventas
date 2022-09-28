package com.ciclo3.ventas.views.controllers;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import com.ciclo3.ventas.util.Login;
import com.ciclo3.ventas.util.Rol;
import com.ciclo3.ventas.util.ItemMenu;
import com.ciclo3.ventas.views.services.EmpleadoServiceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class InicioControllerView {
    public static int TAMAÑO_PAGINA = 10;

    @Autowired
    private EmpleadoServiceView servicio;

    public static List<ItemMenu> generarMenu() {
        List<ItemMenu> menu = new ArrayList<>();

        List<ItemMenu> subMenu = new ArrayList<>();
        ItemMenu itemMenu = new ItemMenu("Empresas", "/menu/empresas", "fa fa-globe",
                new Rol[]{Rol.SUPERADMINISTRADOR});
        menu.add(itemMenu);
        itemMenu = new ItemMenu("Empleados", "/menu/empleados", "fas fa-industry",
                new Rol[]{Rol.SUPERADMINISTRADOR, Rol.ADMINISTRADOR});
        menu.add(itemMenu);
        itemMenu = new ItemMenu("Movimientos", "/menu/movimientos", "fas fa-industry",
                new Rol[]{Rol.ADMINISTRADOR, Rol.OPERATIVO});
        menu.add(itemMenu);
        return menu;
    }

    @GetMapping("/")
    public String inicio(Model modelo, HttpSession sesion) {
//        EmpleadoEntity usuario = (EmpleadoEntity) sesion.getAttribute("usuario");
//        if (usuario == null) {
//            usuario = servicio.login("sergio@email.com", "sergio@email.com");
//            sesion.setAttribute("usuario", usuario);
//        }
//        modelo.addAttribute("usuario", sesion.getAttribute("usuario"));
        modelo.addAttribute("menu", generarMenu());
        modelo.addAttribute("plantilla", "presentacion");
        modelo.addAttribute("login", new Login());
        return "inicio";
    }

    @GetMapping("/menu/{opcion}")
    public String seleccionarMenu(@PathVariable String opcion) {
        String ruta = "redirect:/" + opcion + "/listar/1/" + TAMAÑO_PAGINA;
        return ruta;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("login") Login login, HttpServletRequest request) {
        EmpleadoEntity usuario = servicio.login(login.getUsuario(), login.getClave());
        request.getSession().setAttribute("usuario", usuario);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("usuario", null);
        return "redirect:/";
    }
}
