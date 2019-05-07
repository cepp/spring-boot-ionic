package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Cidade;
import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.domain.Endereco;
import br.com.ceppantoja.cursomc.domain.enums.Perfil;
import br.com.ceppantoja.cursomc.domain.enums.TipoCliente;
import br.com.ceppantoja.cursomc.dto.ClienteDTO;
import br.com.ceppantoja.cursomc.dto.ClienteNewDTO;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.security.UserSS;
import br.com.ceppantoja.cursomc.service.exception.AuthorizationException;
import br.com.ceppantoja.cursomc.service.exception.DataIntegrityException;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ImageService imageService;
    @Value("${img.prefix.client.profile}")
    private String prefix;
    @Value("${img.profile.size}")
    private Integer size;

    public Cliente find(Integer id) {
        UserSS user = this.getUserSS();
        if(!user.hasRole(Perfil.ADMIN) && !user.getId().equals(id)) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = this.repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getName())));
    }

    public List<Cliente> findAll() {
        return this.repo.findAll();
    }

    public Cliente findByEmail(String email) {
        UserSS user = this.getUserSS();

        if(!user.hasRole(Perfil.ADMIN) && !user.getUsername().equals(email)) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = this.repo.findByEmail(email);

        if(obj == null) {
            throw new ObjectNotFoundException(String.format("Objeto não encontrado! Id: %d, Tipo: %s", user.getId(), Cliente.class.getName()));
        }

        return obj;
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
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objNewDTO) {
        Cliente obj = new Cliente(null, objNewDTO.getNome(), objNewDTO.getEmail(), objNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(objNewDTO.getTipo()), this.bCryptPasswordEncoder.encode(objNewDTO.getSenha()));
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

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = getUserSS();

        BufferedImage jpgImage = this.imageService.getJpgFromFile(multipartFile);

        jpgImage = this.imageService.cropSquare(jpgImage);
        jpgImage = this.imageService.resize(jpgImage, size);

        String fileName = String.format("%s%s.jpg", prefix, user.getId());
        InputStream is = this.imageService.getInputStream(jpgImage, "jpg");
        return this.s3Service.uploadFile(is, fileName, "image");
    }

    private UserSS getUserSS() {
        UserSS user = UserService.authenticated();
        if(user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        return user;
    }
}