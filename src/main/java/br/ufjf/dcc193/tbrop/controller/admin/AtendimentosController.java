package br.ufjf.dcc193.tbrop.controller.admin;

import br.ufjf.dcc193.tbrop.model.*;
import br.ufjf.dcc193.tbrop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

@Controller
public class AtendimentosController {

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AtendenteRepository atendenteRepository;

    @Autowired
    EventoRepository eventoRepository;

    @RequestMapping("admin/atendimentos")
    public String index(Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimentos", atendimentoRepository.findAll());
        return "admin/atendimentos/index";
    }

    @RequestMapping("admin/atendimentos/create")
    public String criar(Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimento", new Atendimento());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("atendentes", atendenteRepository.findAll());
        return "admin/atendimentos/create";
    }

    @RequestMapping("admin/atendimentos/store")
    public String store(Atendimento atendimento) {
        atendimento.setStatus(Atendimento.STATUS_EMREVISAO);
        atendimento.setDataCriacao(new Date());
        atendimentoRepository.save(atendimento);

        //setar dados do evento...
        Evento eventoAbertura = new Evento();
        eventoAbertura.setTipo(Evento.TIPO_ABERTURA);
        eventoAbertura.setDataHora(new Date());
        eventoAbertura.setDescricao("Abertura: " + atendimento.getDescricao());
        eventoRepository.save(eventoAbertura);

        return "redirect:/admin/atendimentos";
    }

    @RequestMapping("admin/atendimentos/details/{id}")
    public String detalhes(@PathVariable Long id, Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimento", atendimentoRepository.findById(id));
        model.addAttribute("eventos", eventoRepository.findAllByAtendimento(atendimentoRepository.findById(id).get()));

        return "admin/atendimentos/details";
    }

    @RequestMapping("admin/atendimentos/list/category/{id}")
    public String listByCategoria(@PathVariable Long id, Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimentos", getAtendimentosNaoFechadosByCategoria(categoriaRepository.findById(id).get()));

        return "admin/atendimentos/list-by-category";
    }

    @RequestMapping("admin/atendimentos/list/user/{id}")
    public String listByUsuario(@PathVariable Long id, Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimentos", atendimentoRepository.findAllByUsuario(usuarioRepository.findById(id).get()));
        model.addAttribute("totalAtendimentos", atendimentoRepository.findAllByUsuario(usuarioRepository.findById(id).get()).size());

        return "admin/atendimentos/list-by-user";
    }

    @RequestMapping("admin/atendimentos/list/clerk/{id}")
    public String listByAtendente(@PathVariable Long id, Model model, HttpSession session) {
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimentos", getAtendimentosNaoFechadosByAtendente(atendenteRepository.findById(id).get()));

        return "admin/atendimento/list-by-clerk";
    }

    private List<Atendimento> getAtendimentosNaoFechadosByCategoria(Categoria categoria){
        List<Atendimento> atendimentos = atendimentoRepository.findAllByCategoria(categoria);
        List<Atendimento> atendimentosNaoFechados = new ArrayList<>();

        atendimentos.stream().filter((atendimento) -> (atendimento.getStatus() != 5)).forEachOrdered((atendimento) -> {
            atendimentosNaoFechados.add(atendimento);
        });

        return atendimentosNaoFechados;
    }

    private List<Atendimento> getAtendimentosNaoFechadosByAtendente(Atendente atendente) {
        List<Atendimento> atendimentos = atendimentoRepository.findAllByAtendente(atendente);
        List<Atendimento> atendimentosNaoFechados = new ArrayList<>();

        atendimentos.stream().filter((atendimento) -> (atendimento.getStatus() != 5)).forEachOrdered((atendimento) -> {
            atendimentosNaoFechados.add(atendimento);
        });

        return atendimentosNaoFechados;
    }

    private List<Atendimento> getAtendimentosNaoFechadosByUsuario(Usuario usuario) {
        List<Atendimento> atendimentos = atendimentoRepository.findAllByUsuario(usuario);
        List<Atendimento> atendimentosNaoFechados = new ArrayList<>();

        atendimentos.stream().filter((atendimento) -> (atendimento.getStatus() != 5)).forEachOrdered((atendimento) -> {
            atendimentosNaoFechados.add(atendimento);
        });

        return atendimentosNaoFechados;
    }

    private boolean isLogado(HttpSession session) {
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        return adminUser != null;
    }

}
