package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Categoria;
import br.com.ceppantoja.cursomc.repositories.CategoriaRepository;
import br.com.ceppantoja.cursomc.service.exception.DataIntegrityException;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = this.repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getName())));
    }

    public List<Categoria> findAll() {
        return this.repo.findAll();
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);

        return this.repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        this.find(obj.getId());
        return this.repo.save(obj);
    }

    public void delete(Integer id) {
        this.find(id);
        try {
            this.repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }
}
