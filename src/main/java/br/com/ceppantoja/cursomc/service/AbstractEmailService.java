package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value(value = "${default.sender}")
    private String emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPedido(obj);
        this.sendEmail(simpleMailMessage);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = prepareMimeMessageFromPedido(obj);
            this.sendHtmlEmail(mimeMessage);
        } catch (MessagingException e) {
            this.sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(obj.getCliente().getEmail());
        mimeMessageHelper.setFrom(this.emailSender);
        mimeMessageHelper.setSubject(String.format("Pedido Confirmado! Número Pedido: %d", obj.getId()));
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(this.htmlFromTemplatePedido(obj), true);

        return mimeMessage;
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

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return this.templateEngine.process("email/confirmacaoPedido", context);
    }
}