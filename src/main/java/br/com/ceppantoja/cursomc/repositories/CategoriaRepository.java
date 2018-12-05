package br.com.ceppantoja.cursomc.repositories;

import br.com.ceppantoja.cursomc.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}