package br.com.ceppantoja.cursomc.repositories;

import br.com.ceppantoja.cursomc.domain.Cidade;
import br.com.ceppantoja.cursomc.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}