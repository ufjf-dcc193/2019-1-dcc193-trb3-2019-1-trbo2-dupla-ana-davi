package br.ufjf.dcc193.tbrop.controller.admin;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.model.Categoria;
import br.ufjf.dcc193.tbrop.repository.CategoriaRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoriasController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @RequestMapping("admin/categorias")
    public String index(Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin/categorias/index";
    }

    @RequestMapping("admin/categorias/create")
    public String criar(Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias/create";
    }

    @RequestMapping("admin/categorias/store")
    public String store(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/admin/categorias";
    }

    @RequestMapping("admin/categorias/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("categoria", categoriaRepository.findById(id));
        return "admin/categorias/edit";
    }

    @RequestMapping("admin/categorias/update")
    public String update(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/admin/categorias";
    }

    @RequestMapping("admin/categorias/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/admin/categorias";
    }

    private boolean isLogado(HttpSession session) {
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        return adminUser != null;
    }
}
