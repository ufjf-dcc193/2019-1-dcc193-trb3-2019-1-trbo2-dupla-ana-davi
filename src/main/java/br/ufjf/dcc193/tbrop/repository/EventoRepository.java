package br.ufjf.dcc193.tbrop.repository;

import br.ufjf.dcc193.tbrop.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
