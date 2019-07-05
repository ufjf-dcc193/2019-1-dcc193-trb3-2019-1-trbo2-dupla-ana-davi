package br.ufjf.dcc193.tbrop.repository;

import br.ufjf.dcc193.tbrop.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    
}
