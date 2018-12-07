package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Categoria;
import br.com.ceppantoja.cursomc.domain.Produto;
import br.com.ceppantoja.cursomc.repositories.CategoriaRepository;
import br.com.ceppantoja.cursomc.repositories.ProdutoRepository;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> obj = this.repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Produto.class.getName())));
    }

    public List<Produto> findAll() {
        return this.repo.findAll();
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = !ids.isEmpty() ? this.categoriaRepository.findAllById(ids) : new ArrayList<>();
        return this.repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
