package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.domain.Pedido;
import br.com.ceppantoja.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Pedido> listar() {
        return this.service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> listar(@PathVariable Integer id) {
        Pedido Pedido =  this.service.find(id);
        return ResponseEntity.ok().body(Pedido);
    }
}