package br.com.ceppantoja.cursomc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MockEmailService extends AbstractEmailService {

    private static final Logger log = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulando envio de e-mail");
        log.info(msg.toString());
        log.info("E-mail enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulando envio de e-mail HTML");
        log.info(msg.toString());
        log.info("E-mail enviado");
    }
}
