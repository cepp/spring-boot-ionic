package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.domain.Produto;
import br.com.ceppantoja.cursomc.dto.ProdutoDTO;
import br.com.ceppantoja.cursomc.resources.utils.URL;
import br.com.ceppantoja.cursomc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public List<Produto> listar() {
        return this.service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> listar(@PathVariable Integer id) {
        Produto produto =  this.service.find(id);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ProdutoDTO>> listarByPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> idsCategorias = categorias == null ? new ArrayList<>() : URL.decodeIntList(categorias);

        Page<Produto> produtos = this.service.search(nomeDecoded, idsCategorias, page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(produtos.map(ProdutoDTO::new));
    }
}