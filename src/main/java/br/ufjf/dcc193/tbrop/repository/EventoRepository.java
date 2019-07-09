package br.ufjf.dcc193.tbrop.repository;

import br.ufjf.dcc193.tbrop.model.Atendimento;
import br.ufjf.dcc193.tbrop.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findAllByAtendimento(Atendimento atendimento);
}
