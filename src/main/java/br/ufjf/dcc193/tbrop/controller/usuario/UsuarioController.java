package br.ufjf.dcc193.tbrop.controller.usuario;

import br.ufjf.dcc193.tbrop.model.Atendimento;
import br.ufjf.dcc193.tbrop.model.Evento;
import br.ufjf.dcc193.tbrop.model.Usuario;
import br.ufjf.dcc193.tbrop.repository.AtendimentoRepository;
import br.ufjf.dcc193.tbrop.repository.EventoRepository;
import br.ufjf.dcc193.tbrop.repository.UsuarioRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @Autowired
    EventoRepository eventoRepository;
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/usuario")
    public String index(Model model, HttpSession session) {
        if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Usuario user = (Usuario) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("atendimentos", atendimentoRepository.findAllByUsuario(user));
        model.addAttribute("totalAtendimentos", atendimentoRepository.findAllByUsuario(user).size());
        return "usuario/index";
    }

    @RequestMapping("usuario/atendimentos/details/{id}")
    public String detalhes(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Usuario user = (Usuario) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("atendimento", atendimentoRepository.getOne(id));
        model.addAttribute("eventos", eventoRepository.findAllByAtendimento(atendimentoRepository.findById(id).get()));

        return "usuario/atendimento-details";
    }

    @RequestMapping("usuario/atendimentos/update/{id}")
    public String updateStatus(Atendimento atendUp, @PathVariable Long id, HttpSession session) {
        Atendimento atendimento = atendimentoRepository.getOne(id);

        Date data = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Usuario user = (Usuario) session.getAttribute("user");

        String statusOld = atendimento.getStatusNome();

        atendimento.setStatus(atendUp.getStatus());
        atendimento.setDescricao(atendUp.getDescricao());

        Evento evento1 = new Evento();
        evento1.setDataHora(formato.format(data));
        evento1.setAtendimento(atendimento);
        evento1.setTipo(Evento.TIPO_ALTERACAO_STATUS);
        evento1.setDescricao("Antigo: " + statusOld + " - " + atendUp.getDescricao() + " - Usuário: " + user.getNome());

        Evento evento2 = new Evento();
        evento2.setDataHora(formato.format(data));
        evento2.setAtendimento(atendimento);

        if (atendUp.getStatus() == Atendimento.STATUS_ABERTO) {
            atendimento.setDataFechamento(null);
            evento2.setTipo(Evento.TIPO_ABERTURA);
            evento2.setDescricao("Usuário: " + user.getNome());
        }

        if (atendUp.getStatus() == Atendimento.STATUS_FECHADO) {
            atendimento.setDataFechamento(formato.format(data));
            evento2.setTipo(Evento.TIPO_FECHAMENTO);
            evento2.setDescricao("Usuário: " + user.getNome());
        }

        atendimentoRepository.save(atendimento);
        eventoRepository.save(evento1);
        eventoRepository.save(evento2);

        return "redirect:/usuario/atendimentos/details/" + id;
    }
    
    @RequestMapping("usuario/perfil")
    public String perfil(Model model, HttpSession session){
         if (!isLogado(session)) {
            return "redirect:/logout";
        }
        Usuario user = (Usuario) session.getAttribute("user");
        model.addAttribute("usuario", user);
      
        return "usuario/perfil";
    }
    
    @RequestMapping("usuario/perfil/update")
    public String storePerfil(Usuario usuario, HttpSession session){
        usuarioRepository.save(usuario);
        session.setAttribute("user", usuario);
        return "redirect:/usuario/perfil";
    }

    private boolean isLogado(HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("user");
        return user != null;
    }

}
