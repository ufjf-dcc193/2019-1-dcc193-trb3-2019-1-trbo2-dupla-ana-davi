package br.ufjf.dcc193.tbrop.controller;

import br.ufjf.dcc193.tbrop.model.*;
import br.ufjf.dcc193.tbrop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AtendimentoController {

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

    @RequestMapping("atendimentos")
    public String index(Model model) {
        model.addAttribute("atendimentos", atendimentoRepository.findAll());
        return "atendimento/index";
    }

    @RequestMapping("atendimentos/create")
    public String criar(Model model) {
        model.addAttribute("atendimento", new Atendimento());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("atendentes", atendenteRepository.findAll());
        return "atendimento/create";
    }

    @RequestMapping("atendimentos/store")
    public String store(Atendimento atendimento) {

        atendimento.setStatus(1);
        atendimentoRepository.save(atendimento);

        //setar dados do evento...
        //setar dados do evento...
        //setar dados do evento...
        Evento eventoAbertura = new Evento();
        eventoAbertura.setTipo(1);

        eventoRepository.save(eventoAbertura);

        return "redirect:/atendimentos";
    }

    @RequestMapping("atendimentos/details/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("atendimento", atendimentoRepository.findById(id));
        model.addAttribute("eventos", eventoRepository.findAllByAtendimento(atendimentoRepository.findById(id).get()));

        return "atendimento/details";
    }

    @RequestMapping("atendimentos/list/category/{id}")
    public String listByCategoria(@PathVariable Long id, Model model) {
        model.addAttribute("atendimentos", getAtendimentosNaoFechadosByCategoria(categoriaRepository.findById(id).get()));

        return "atendimento/list-by-category";
    }

    @RequestMapping("atendimentos/list/user/{id}")
    public String listByUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("atendimentos", atendimentoRepository.findAllByUsuario(usuarioRepository.findById(id).get()));
        model.addAttribute("totalAtendimentos", atendimentoRepository.findAllByUsuario(usuarioRepository.findById(id).get()).size());

        return "atendimento/list-by-user";
    }

    @RequestMapping("atendimentos/list/clerk/{id}")
    public String listByAtendente(@PathVariable Long id, Model model) {
        model.addAttribute("atendimentos", getAtendimentosNaoFechadosByAtendente(atendenteRepository.findById(id).get()));

        return "atendimento/list-by-clerk";
    }

    private List<Atendimento> getAtendimentosNaoFechadosByCategoria(Categoria categoria){
        List<Atendimento> atendimentos = atendimentoRepository.findAllByCategoria(categoria);
        List<Atendimento> atendimentosNaoFechados = new ArrayList<>();

        for (Atendimento atendimento : atendimentos) {
            if(atendimento.getStatus() != 5)
                atendimentosNaoFechados.add(atendimento);
        }

        return atendimentosNaoFechados;
    }

    private List<Atendimento> getAtendimentosNaoFechadosByAtendente(Atendente atendente) {
        List<Atendimento> atendimentos = atendimentoRepository.findAllByAtendente(atendente);
        List<Atendimento> atendimentosNaoFechados = new ArrayList<>();

        for (Atendimento atendimento : atendimentos) {
            if(atendimento.getStatus() != 5)
                atendimentosNaoFechados.add(atendimento);
        }

        return atendimentosNaoFechados;
    }

    private List<Atendimento> getAtendimentosNaoFechadosByUsuario(Usuario usuario) {
        List<Atendimento> atendimentos = atendimentoRepository.findAllByUsuario(usuario);
        List<Atendimento> atendimentosNaoFechados = new ArrayList<>();

        for (Atendimento atendimento : atendimentos) {
            if(atendimento.getStatus() != 5)
                atendimentosNaoFechados.add(atendimento);
        }

        return atendimentosNaoFechados;
    }


}
