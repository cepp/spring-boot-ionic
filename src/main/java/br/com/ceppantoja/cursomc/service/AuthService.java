package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder pe;

    private Random random = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = this.clienteRepository.findByEmail(email);

        if(cliente == null) {
            throw new ObjectNotFoundException("E-mail não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(this.pe.encode(newPass));

        this.clienteRepository.save(cliente);

        this.emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];

        for(int i = 0; i < vet.length; i++) {
            vet[i] = randomChar();
        }

        return null;
    }

    private char randomChar() {
        int opt = this.random.nextInt(3);
        switch (opt) {
            case 0: // gera numérico
                return (char) (this.random.nextInt(10) + 48);
            case 1: // gera letra minúscula
                return (char) (this.random.nextInt(26) + 65);
            default: // gera letra maiúscula
                return (char) (this.random.nextInt(26) + 97);
        }
    }
}
