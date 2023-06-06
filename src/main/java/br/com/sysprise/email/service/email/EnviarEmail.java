package br.com.sysprise.email.service.email;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnviarEmail {


    private final JavaMailSender mailSender;
    private final BuscarContato buscarContato;
    private final MontarEmail montarEmail;


    public void enviar(Long pessoaId, String assunto, String corpo, byte[] bytes) throws MessagingException {
        MimeMessageHelper helper = montarEmail.montar(pessoaId, assunto, corpo, mailSender, buscarContato);
        helper.addAttachment("boleto.pdf", new ByteArrayResource(bytes));
        mailSender.send(helper.getMimeMessage());
    }

    public void enviar(Long pessoaId, String assunto, String corpo) throws MessagingException {
        MimeMessageHelper helper = montarEmail.montar(pessoaId, assunto, corpo, mailSender, buscarContato);
        mailSender.send(helper.getMimeMessage());
    }

}
