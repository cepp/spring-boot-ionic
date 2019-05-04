package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.domain.Categoria;
import br.com.ceppantoja.cursomc.domain.Pedido;
import br.com.ceppantoja.cursomc.dto.CategoriaDTO;
import br.com.ceppantoja.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping
    public List<Pedido> listar() {
        return this.service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> listar(@PathVariable Integer id) {
        Pedido Pedido =  this.service.find(id);
        return ResponseEntity.ok().body(Pedido);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
        Pedido novoObjeto = this.service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoObjeto.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Pedido>> listarByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Pedido> pedidos = this.service.findByPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(pedidos);
    }
}