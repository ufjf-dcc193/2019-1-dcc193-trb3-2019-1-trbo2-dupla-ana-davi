package br.ufjf.dcc193.tbrop.controller.admin;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.model.Usuario;
import br.ufjf.dcc193.tbrop.repository.UsuarioRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuariosController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("admin/usuarios")
    public String index(Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios/index";
    }

    @RequestMapping("admin/usuarios/create")
    public String criar(Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/create";
    }

    @RequestMapping("admin/usuarios/store")
    public String store(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @RequestMapping("admin/usuarios/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("usuario", usuarioRepository.findById(id));
        return "admin/usuarios/edit";
    }

    @RequestMapping("admin/usuarios/update")
    public String update(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @RequestMapping("admin/usuarios/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }

    private boolean isLogado(HttpSession session) {
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        return adminUser != null;
    }
}
