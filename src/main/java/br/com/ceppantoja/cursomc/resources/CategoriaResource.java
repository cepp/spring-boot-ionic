package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.domain.Categoria;
import br.com.ceppantoja.cursomc.dto.CategoriaDTO;
import br.com.ceppantoja.cursomc.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = this.service.findAll();
        return categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> listar(@PathVariable Integer id) {
        Categoria categoria =  this.service.find(id);
        return ResponseEntity.ok().body(new CategoriaDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria novoObjeto = this.service.insert(this.service.fromDTO(categoriaDTO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoObjeto.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        categoriaDTO.setId(id);
        this.service.update(this.service.fromDTO(categoriaDTO));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> listarByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> categorias = this.service.findByPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(categorias.map(CategoriaDTO::new));
    }
}