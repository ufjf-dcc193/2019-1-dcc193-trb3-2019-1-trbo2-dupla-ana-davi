package br.ufjf.dcc193.tbrop.controller.admin;

import br.ufjf.dcc193.tbrop.model.Atendente;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String index(Model model, HttpSession session) {
        if (isLogado(session)) {
            Atendente adminUser = (Atendente) session.getAttribute("adminUser");
            model.addAttribute("adminUser", adminUser);
            return "admin/index";
        } else {
            return "redirect:/logout";
        }
    }

    private boolean isLogado(HttpSession session) {
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        return adminUser != null;
    }

}
