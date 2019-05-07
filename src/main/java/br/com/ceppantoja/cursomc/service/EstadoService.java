package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Estado;
import br.com.ceppantoja.cursomc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository repo;

    public List<Estado> findAll() {
        return this.repo.findAllByOrderByNome();
    }

    public Estado findById(Integer id) {
        return this.repo.findById(id).orElse(null);
    }
}
