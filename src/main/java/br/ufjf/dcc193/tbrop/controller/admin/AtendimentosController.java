package br.ufjf.dcc193.tbrop.controller.admin;

import br.ufjf.dcc193.tbrop.model.*;
import br.ufjf.dcc193.tbrop.repository.*;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        return "admin/atendimentos/create";
    }
    
    @RequestMapping("admin/atendimentos/store")
    public String store(Atendimento atendimento, HttpSession session) {
        Date dataCriacao = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        
        atendimento.setAtendente(adminUser);
        atendimento.setStatus(Atendimento.STATUS_EMREVISAO);
        atendimento.setDataCriacao(formato.format(dataCriacao));
        atendimentoRepository.save(atendimento);

        //setar dados do evento...
        Evento eventoAbertura = new Evento();
        eventoAbertura.setAtendimento(atendimento);
        eventoAbertura.setTipo(Evento.TIPO_ABERTURA);
        eventoAbertura.setDataHora(formato.format(dataCriacao));
        eventoAbertura.setDescricao(atendimento.getDescricao() + " - Atendente Responsável: " + adminUser.getNome());
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
        model.addAttribute("atendimento", atendimentoRepository.getOne(id));
        model.addAttribute("eventos", eventoRepository.findAllByAtendimento(atendimentoRepository.findById(id).get()));
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        
        return "admin/atendimentos/details";
    }
    
    @RequestMapping("admin/atendimentos/update/{id}/usuario")
    public String updateUsuario(Atendimento atendUp, @PathVariable Long id, HttpSession session) {
        Atendimento atendimento = atendimentoRepository.getOne(id);
        
        if (atendimento.getDataFechamento() == null) {
            Date data = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Atendente adminUser = (Atendente) session.getAttribute("adminUser");
            Usuario userOld = atendimento.getUsuario();
            
            atendimento.setUsuario(atendUp.getUsuario());
            atendimento.setDescricao(atendUp.getDescricao());
            
            if (!Objects.equals(adminUser.getId(), atendimento.getAtendente().getId())) {
                Atendente atendOld = atendimento.getAtendente();
                atendimento.setAtendente(adminUser);
                
                Evento eventoAtendente = new Evento();
                eventoAtendente.setAtendimento(atendimento);
                eventoAtendente.setDataHora(formato.format(data));
                eventoAtendente.setTipo(Evento.TIPO_ALTERACAO_ATENDENTE);
                eventoAtendente.setDescricao("Antigo: " + atendOld.getNome());
                eventoRepository.save(eventoAtendente);
            }
            
            atendimentoRepository.save(atendimento);
            
            Evento evento = new Evento();
            evento.setDataHora(formato.format(data));
            evento.setAtendimento(atendimento);
            evento.setTipo(Evento.TIPO_ALTERACAO_USUARIO);
            evento.setDescricao("Antigo: " + userOld.getNome() + " - " + atendUp.getDescricao() + " - Atendente Responsável: " + adminUser.getNome());
            eventoRepository.save(evento);
        }
        
        return "redirect:/admin/atendimentos/details/" + id;
    }
    
    @RequestMapping("admin/atendimentos/update/{id}/categoria")
    public String updateCategoria(Atendimento atendUp, @PathVariable Long id, HttpSession session) {
        Atendimento atendimento = atendimentoRepository.getOne(id);
        
        if (atendimento.getDataFechamento() == null) {
            Date data = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Atendente adminUser = (Atendente) session.getAttribute("adminUser");
            
            Categoria catOld = atendimento.getCategoria();
            
            atendimento.setCategoria(atendUp.getCategoria());
            atendimento.setDescricao(atendUp.getDescricao());
            
            if (!Objects.equals(adminUser.getId(), atendimento.getAtendente().getId())) {
                Atendente atendOld = atendimento.getAtendente();
                atendimento.setAtendente(adminUser);
                
                Evento eventoAtendente = new Evento();
                eventoAtendente.setAtendimento(atendimento);
                eventoAtendente.setDataHora(formato.format(data));
                eventoAtendente.setTipo(Evento.TIPO_ALTERACAO_ATENDENTE);
                eventoAtendente.setDescricao("Antigo: " + atendOld.getNome());
                eventoRepository.save(eventoAtendente);
            }
            
            atendimentoRepository.save(atendimento);
            
            Evento evento = new Evento();
            evento.setDataHora(formato.format(data));
            evento.setAtendimento(atendimento);
            evento.setTipo(Evento.TIPO_ALTERACAO_CATEGORIA);
            evento.setDescricao("Antiga: " + catOld.getTitulo() + " - " + atendUp.getDescricao() + " - Atendente Responsável: " + adminUser.getNome());
            eventoRepository.save(evento);
        }
        return "redirect:/admin/atendimentos/details/" + id;
    }
    
    @RequestMapping("admin/atendimentos/update/{id}/status")
    public String updateStatus(Atendimento atendUp, @PathVariable Long id, HttpSession session) {
        Atendimento atendimento = atendimentoRepository.getOne(id);
        if (atendimento.getDataFechamento() == null) {
            Date data = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Atendente adminUser = (Atendente) session.getAttribute("adminUser");
            
            String statusOld = atendimento.getStatusNome();
            
            atendimento.setStatus(atendUp.getStatus());
            atendimento.setDescricao(atendUp.getDescricao());
            
            if (!Objects.equals(adminUser.getId(), atendimento.getAtendente().getId())) {
                Atendente atendOld = atendimento.getAtendente();
                atendimento.setAtendente(adminUser);
                
                Evento eventoAtendente = new Evento();
                eventoAtendente.setAtendimento(atendimento);
                eventoAtendente.setDataHora(formato.format(data));
                eventoAtendente.setTipo(Evento.TIPO_ALTERACAO_ATENDENTE);
                eventoAtendente.setDescricao("Antigo: " + atendOld.getNome());
                eventoRepository.save(eventoAtendente);
            }
            
            atendimentoRepository.save(atendimento);
            
            Evento evento = new Evento();
            evento.setDataHora(formato.format(data));
            evento.setAtendimento(atendimento);
            evento.setTipo(Evento.TIPO_ALTERACAO_STATUS);
            evento.setDescricao("Antigo: " + statusOld + " - " + atendUp.getDescricao() + " - Atendente Responsável: " + adminUser.getNome());
            eventoRepository.save(evento);
        }
        
        return "redirect:/admin/atendimentos/details/" + id;
    }
    
    @RequestMapping("admin/atendimentos/close/{id}")
    public String close(@PathVariable Long id, HttpSession session) {
        Atendimento atendimento = atendimentoRepository.getOne(id);
        if (atendimento.getDataFechamento() == null) {
            Date data = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Atendente adminUser = (Atendente) session.getAttribute("adminUser");
            
            atendimento.setDataFechamento(formato.format(data));
            atendimento.setStatus(Atendimento.STATUS_FECHADO);
            
            if (!Objects.equals(adminUser.getId(), atendimento.getAtendente().getId())) {
                Atendente atendOld = atendimento.getAtendente();
                atendimento.setAtendente(adminUser);
                
                Evento eventoAtendente = new Evento();
                eventoAtendente.setAtendimento(atendimento);
                eventoAtendente.setDataHora(formato.format(data));
                eventoAtendente.setTipo(Evento.TIPO_ALTERACAO_ATENDENTE);
                eventoAtendente.setDescricao("Antigo: " + atendOld.getNome());
                eventoRepository.save(eventoAtendente);
            }
            
            atendimentoRepository.save(atendimento);
            
            Evento evento = new Evento();
            evento.setDataHora(formato.format(data));
            evento.setAtendimento(atendimento);
            evento.setTipo(Evento.TIPO_FECHAMENTO);
            evento.setDescricao("Atendente Responsável: " + adminUser.getNome());
            eventoRepository.save(evento);
        }
        
        return "redirect:/admin/atendimentos/details/" + id;
    }
    
    @RequestMapping("admin/atendimentos/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        List<Evento> eventos = eventoRepository.findAllByAtendimento(atendimentoRepository.findById(id).get());
        eventos.forEach((evento) -> {
            eventoRepository.delete(evento);
        });
        atendimentoRepository.deleteById(id);
        return "redirect:/admin/atendimentos";
    }
    
    @RequestMapping("admin/atendimentos/list/category/{id}")
    public String listByCategoria(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Atendente adminUser = (Atendente) session.getAttribute("adminUser");
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("atendimentos", getAtendimentosNaoFechadosByCategoria(categoriaRepository.findById(id).get()));
        model.addAttribute("categoria", categoriaRepository.findById(id).get());
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
        model.addAttribute("usuario", usuarioRepository.findById(id).get());
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
        model.addAttribute("atendente", atendenteRepository.findById(id).get());
        return "admin/atendimentos/list-by-clerk";
    }
    
    private List<Atendimento> getAtendimentosNaoFechadosByCategoria(Categoria categoria) {
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
