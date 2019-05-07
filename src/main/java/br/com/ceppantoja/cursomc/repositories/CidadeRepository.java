package br.com.ceppantoja.cursomc.repositories;

import br.com.ceppantoja.cursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    @Transactional(readOnly = true)
    List<Cidade> findCidadesByEstado_IdOrderByNome(Integer estado_id);
}