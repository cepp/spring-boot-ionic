package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.dto.ClienteDTO;
import br.com.ceppantoja.cursomc.dto.ClienteNewDTO;
import br.com.ceppantoja.cursomc.service.ClienteService;
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

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> listar() {
        return this.service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> listar(@PathVariable Integer id) {
        Cliente cliente =  this.service.find(id);
        return ResponseEntity.ok().body(new ClienteDTO(cliente));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO categoriaNewDTO) {
        Cliente obj = this.service.fromDTO(categoriaNewDTO);

        Cliente novoObjeto = this.service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoObjeto.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        this.service.update(this.service.fromDTO(clienteDTO));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> listarByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> categorias = this.service.findByPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(categorias.map(ClienteDTO::new));
    }
}