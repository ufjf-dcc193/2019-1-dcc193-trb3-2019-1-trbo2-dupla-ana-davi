package br.ufjf.dcc193.tbrop.repository;

import br.ufjf.dcc193.tbrop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
