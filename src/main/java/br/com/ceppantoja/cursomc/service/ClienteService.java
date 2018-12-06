package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Cidade;
import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.domain.Endereco;
import br.com.ceppantoja.cursomc.domain.enums.TipoCliente;
import br.com.ceppantoja.cursomc.dto.ClienteDTO;
import br.com.ceppantoja.cursomc.dto.ClienteNewDTO;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.service.exception.DataIntegrityException;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {
        Optional<Cliente> obj = this.repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName())));
    }

    public List<Cliente> findAll() {
        return this.repo.findAll();
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);

        return this.repo.save(obj);
    }

    public Cliente update(Cliente obj) {
        Cliente objToUpdate = this.find(obj.getId());

        this.updateData(objToUpdate, obj);

        return this.repo.save(objToUpdate);
    }

    public void delete(Integer id) {
        this.find(id);
        try {
            this.repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
        }
    }

    public Page<Cliente> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        return this.repo.findAll(PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy));
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objNewDTO) {
        Cliente obj = new Cliente(null, objNewDTO.getNome(), objNewDTO.getEmail(), objNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(objNewDTO.getTipo()));
        Cidade cidade = new Cidade(objNewDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, objNewDTO.getLogradouro(), objNewDTO.getNumero(), objNewDTO.getComplemento(), objNewDTO.getBairro(), objNewDTO.getCep(), obj, cidade);
        obj.getEnderecos().add(endereco);
        obj.getTelefones().add(objNewDTO.getTelefone1());
        if(objNewDTO.getTelefone2() != null) {
            obj.getTelefones().add(objNewDTO.getTelefone2());
        }
        if(objNewDTO.getTelefone3() != null) {
            obj.getTelefones().add(objNewDTO.getTelefone3());
        }

        return obj;
    }

    private void updateData(Cliente objToUpdate, Cliente obj) {
        objToUpdate.setNome(obj.getNome());
        objToUpdate.setEmail(obj.getEmail());
    }
}