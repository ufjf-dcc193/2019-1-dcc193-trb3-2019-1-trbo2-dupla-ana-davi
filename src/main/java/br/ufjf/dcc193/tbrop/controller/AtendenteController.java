package br.ufjf.dcc193.tbrop.controller;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.repository.AtendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AtendenteController {

    @Autowired
    AtendenteRepository atendenteRepository;

    @RequestMapping("atendentes")
    public String index(Model model) {
        model.addAttribute("atendentes", atendenteRepository.findAll());
        return "atendente/index";
    }

    @RequestMapping("atendentes/create")
    public String criar(Model model) {
        model.addAttribute("atendente", new Atendente());
        return "atendente/create";
    }

    @RequestMapping("atendentes/store")
    public String store(Atendente atendente) {
        atendenteRepository.save(atendente);
        return "redirect:/atendentes";
    }

    @RequestMapping("atendentes/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("atendente", atendenteRepository.findById(id));
        return "atendente/edit";
    }

    @RequestMapping("atendentes/update")
    public String update(Atendente atendente) {
        atendenteRepository.save(atendente);
        return "redirect:/atendentes";
    }

    @RequestMapping("atendentes/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        atendenteRepository.deleteById(id);
        return "redirect:/atendentes";
    }
}
