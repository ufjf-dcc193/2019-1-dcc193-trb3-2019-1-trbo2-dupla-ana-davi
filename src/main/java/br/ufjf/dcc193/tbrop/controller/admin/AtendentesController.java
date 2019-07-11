package br.ufjf.dcc193.tbrop.controller.admin;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.repository.AtendenteRepository;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AtendentesController {

    @Autowired
    AtendenteRepository atendenteRepository;

    @RequestMapping("admin/atendentes")
    public String index(Model model, HttpSession session) {
        if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendentes", atendenteRepository.findAll());
        return "admin/atendentes/index";
    }

    @RequestMapping("admin/atendentes/create")
    public String criar(Model model, HttpSession session) {
        if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendente", new Atendente());
        return "admin/atendentes/create";
    }

    @RequestMapping("admin/atendentes/store")
    public String store(Atendente atendente) {
        atendenteRepository.save(atendente);
        return "redirect:/admin/atendentes";
    }

    @RequestMapping("admin/atendentes/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendente", atendenteRepository.findById(id));
        return "admin/atendentes/edit";
    }

    @RequestMapping("admin/atendentes/update")
    public String update(Atendente atendente) {
        atendenteRepository.save(atendente);
        return "redirect:/admin/atendentes";
    }

    @RequestMapping("admin/atendentes/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        atendenteRepository.deleteById(id);
        return "redirect:/admin/atendentes";
    }

    private boolean isLogado(HttpSession session) {
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        return adminUser != null;
    }
}
