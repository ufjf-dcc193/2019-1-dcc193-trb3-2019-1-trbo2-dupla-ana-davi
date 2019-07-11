package br.ufjf.dcc193.tbrop.controller;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.model.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"", "/"})
    public String index(HttpSession session) {
        Atendente atendente = (Atendente) session.getAttribute("atendente");
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (atendente == null && usuario == null) {
            return "redirect:/singin/perfil";
        }
        return "index";
    }

    @RequestMapping({"singin/perfil"})
    public String perfilLogin() {
        return "home/perfil";
    }

    @RequestMapping({"singin/atendentes"})
    public String singinAtendente(Model model, HttpSession session) {
        Atendente atendente = (Atendente) session.getAttribute("atendente");

        if (atendente == null) {
            model.addAttribute("atendente", new Atendente());
            return "home/singin-atendente";
        }
        return "index";
    }
    
    @RequestMapping({"singin/usuarios"})
    public String singinUsuario(Model model, HttpSession session) {
         Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            model.addAttribute("usuario", new Usuario());
            return "home/singin-usuario";
        }
        return "index";
    }
}
