package br.ufjf.dcc193.tbrop.controller;

import br.ufjf.dcc193.tbrop.model.Categoria;
import br.ufjf.dcc193.tbrop.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @RequestMapping("categorias")
    public String index(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categoria/index";
    }

    @RequestMapping("categorias/create")
    public String criar(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/create";
    }

    @RequestMapping("categorias/store")
    public String store(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @RequestMapping("categorias/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaRepository.findById(id));
        return "categoria/edit";
    }

    @RequestMapping("categorias/update")
    public String update(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @RequestMapping("admin/categorias/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
}
