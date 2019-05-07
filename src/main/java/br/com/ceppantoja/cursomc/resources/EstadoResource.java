package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.domain.Cidade;
import br.com.ceppantoja.cursomc.domain.Estado;
import br.com.ceppantoja.cursomc.dto.CidadeDTO;
import br.com.ceppantoja.cursomc.dto.EstadoDTO;
import br.com.ceppantoja.cursomc.service.CidadeService;
import br.com.ceppantoja.cursomc.service.EstadoService;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;
    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listar() {
        return ResponseEntity.ok().body(this.parserEstadoToEstadoDTO(this.service.findAll()));
    }

    @GetMapping(value = "/{estado_id}/cidades")
    public ResponseEntity<List<CidadeDTO>> getCidadesByEstadoId(@PathVariable(value = "estado_id") Integer estadoId) {
        return ResponseEntity.ok().body(this.parserCidadeToCidadeDTO(this.cidadeService.findByEstadoId(estadoId)));
    }

    private List<EstadoDTO> parserEstadoToEstadoDTO(List<Estado> estados) {
        if(Collections.isEmpty(estados)) {
            throw new ObjectNotFoundException("Nenhum estado encontrado");
        }

        return estados.stream().map(EstadoDTO::new).collect(Collectors.toList());
    }

    private List<CidadeDTO> parserCidadeToCidadeDTO(List<Cidade> cidades) {
        if(Collections.isEmpty(cidades)) {
            throw new ObjectNotFoundException("Nenhuma cidade encontrada para o estado");
        }

        return cidades.stream().map(CidadeDTO::new).collect(Collectors.toList());
    }
}
