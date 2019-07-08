package br.ufjf.dcc193.tbrop.controller;

import br.ufjf.dcc193.tbrop.model.Usuario;
import br.ufjf.dcc193.tbrop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("usuarios")
    public String index(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuario/index";
    }

    @RequestMapping("admin/usuarios/create")
    public String criar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/create";
    }

    @RequestMapping("usuarios/store")
    public String store(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @RequestMapping("usuarios/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id));
        return "usuario/edit";
    }

    @RequestMapping("usuarios/update")
    public String update(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @RequestMapping("usuarios/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }

}
