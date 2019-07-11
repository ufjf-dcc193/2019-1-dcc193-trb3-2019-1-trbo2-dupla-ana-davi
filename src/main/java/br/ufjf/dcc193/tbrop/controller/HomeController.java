package br.ufjf.dcc193.tbrop.controller;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.model.Usuario;
import br.ufjf.dcc193.tbrop.repository.AtendenteRepository;
import br.ufjf.dcc193.tbrop.repository.UsuarioRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    AtendenteRepository atendenteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping({"", "/"})
    public String index(HttpSession session) {
        Atendente atendente = (Atendente) session.getAttribute("adminUser");
        Usuario usuario = (Usuario) session.getAttribute("user");

        if (atendente == null && usuario == null) {
            return "redirect:/singin/perfil";
        } else if (atendente != null) { //logado como atendente
            return "redirect:/admin";
        } else { //logado como usuário
            return "redirect:/usuario";
        }
    }

    @RequestMapping({"singin/perfil"})
    public String perfilLogin(HttpSession session) {
        Atendente atendente = (Atendente) session.getAttribute("adminUser");
        Usuario usuario = (Usuario) session.getAttribute("user");

        if (atendente != null) { //logado como atendente
            return "redirect:/admin";
        }

        if (usuario != null) { //logado como usuário
            return "redirect:/usuario";
        }
        return "home/perfil";
    }

    @RequestMapping({"singin/atendente"})
    public String singinAtendente(Model model, HttpSession session) {
        Atendente atendente = (Atendente) session.getAttribute("adminUser");

        if (atendente == null) {
            model.addAttribute("atendente", new Atendente());
            return "home/singin-atendente";
        }
        model.addAttribute("adminUser", atendente);
        return "admin/index";
    }

    @RequestMapping({"login/atendente"})
    public String loginAtendente(Atendente atendente, HttpSession session) {
        Atendente adminUser = atendenteRepository.findFirstByEmailAndSenha(atendente.getEmail(), atendente.getSenha());

        if (adminUser != null) {
            session.setAttribute("adminUser", adminUser);
            session.setAttribute("user", null);
            return "redirect:/admin";
        }
        return "redirect:/singin/atendente";
    }

    @RequestMapping({"singin/usuario"})
    public String singinUsuario(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("user");

        if (usuario == null) {
            model.addAttribute("usuario", new Usuario());
            return "home/singin-usuario";
        }
        model.addAttribute("user", usuario);
        return "usuario/index";
    }

    @RequestMapping({"login/usuario"})
    public String loginUsuario(Usuario usuario, HttpSession session) {
        Usuario user = usuarioRepository.findFirstByEmailAndSenha(usuario.getEmail(), usuario.getSenha());

        if (user != null) {
            session.setAttribute("adminUser", null);
            session.setAttribute("user", user);
            return "redirect:/usuario";
        }
        return "redirect:/singin/usuario";
    }

    @RequestMapping({"logout"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/singin/perfil";
    }

    @RequestMapping({"singup/atendente"})
    public String singupAtendente(Model model) {
        model.addAttribute("atendente", new Atendente());
        return "home/singup-atendente";
    }

    @RequestMapping({"singup/atendente/save"})
    public String registerAtendente(Atendente atendente) {
        atendenteRepository.save(atendente);
        return "redirect:/singin/atendente";
    }

    @RequestMapping({"singup/usuario"})
    public String singupUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "home/singup-usuario";
    }
    
    @RequestMapping({"singup/usuario/save"})
    public String registerUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/singin/usuario";
    }
}
