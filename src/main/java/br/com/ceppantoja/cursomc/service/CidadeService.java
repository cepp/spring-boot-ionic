package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Cidade;
import br.com.ceppantoja.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository repo;

    public List<Cidade> findByEstadoId(Integer estadoId) {
        return this.repo.findCidadesByEstado_IdOrderByNome(estadoId);
    }
}
