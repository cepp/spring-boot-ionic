package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value(value = "${default.sender}")
    private String emailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPedido(obj);
        this.sendEmail(simpleMailMessage);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(obj.getCliente().getEmail());
        simpleMailMessage.setFrom(this.emailSender);
        simpleMailMessage.setSubject(String.format("Pedido Confirmado! Número Pedido: %d", obj.getId()));
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText(obj.toString());

        return simpleMailMessage;
    }
}