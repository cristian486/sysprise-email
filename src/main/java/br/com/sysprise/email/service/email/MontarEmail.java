package br.com.sysprise.email.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pb.Contato;

@Component
public class MontarEmail {


    public MimeMessageHelper montar(Long pessoaId, String assunto, String corpo, JavaMailSender mailSender, BuscarContato buscarContato) throws MessagingException {
        Contato contato = buscarContato.buscarContato(pessoaId);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("sysprisecompany@gmail.com");
        helper.setTo(contato.getEmail());
        helper.setSubject(assunto);
        helper.setText(corpo, true);
        return helper;
    }
}
