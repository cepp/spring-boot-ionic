package br.com.ceppantoja.cursomc.dto;

import br.com.ceppantoja.cursomc.domain.Cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotEmpty(message = "Nome é obrigatório")
    @Size(min = 5, max = 120, message = "Tamanho deve ser entre {min} e {max} caracteres")
    private String nome;
    @NotEmpty(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
