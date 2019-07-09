package br.ufjf.dcc193.tbrop.repository;

import br.ufjf.dcc193.tbrop.model.Atendente;
import br.ufjf.dcc193.tbrop.model.Atendimento;
import br.ufjf.dcc193.tbrop.model.Categoria;
import br.ufjf.dcc193.tbrop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findAllByAtendente(Atendente atendente);

    List<Atendimento> findAllByUsuario(Usuario usuario);

    List<Atendimento> findAllByCategoria(Categoria categoria);
}
