package br.com.ceppantoja.cursomc.config;

import br.com.ceppantoja.cursomc.service.DBService;
import br.com.ceppantoja.cursomc.service.EmailService;
import br.com.ceppantoja.cursomc.service.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile(value = "test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantializeDatabase() throws ParseException {
        this.dbService.instatiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}