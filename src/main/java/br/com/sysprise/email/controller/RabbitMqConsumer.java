package br.com.sysprise.email.controller;

import br.com.sysprise.email.model.DadosCompraMaterial;
import br.com.sysprise.email.model.DadosEmailCobranca;
import br.com.sysprise.email.service.MontarPedidoCompra;
import br.com.sysprise.email.service.boleto.GeradorBoleto;
import br.com.sysprise.email.service.email.EnviarEmail;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConsumer {

    private final EnviarEmail enviarEmail;
    private final GeradorBoleto geradorBoleto;
    private final MontarPedidoCompra montarPedido;

    public RabbitMqConsumer(EnviarEmail enviarEmail, GeradorBoleto geradorBoleto, MontarPedidoCompra montarPedido) {
        this.enviarEmail = enviarEmail;
        this.geradorBoleto = geradorBoleto;
        this.montarPedido = montarPedido;
    }

    private String mensagemEmailCobranca = "<h2>Prezado(a) Cliente,</h2><p>Obrigado por realizar sua compra em nossa loja. Seu boleto segue em anexo a este e-mail</p><p>Por favor, efetue o pagamento até a data de vencimento para evitar atrasos.</p><p>Qualquer dúvida, estamos à disposição.</p><p>Atenciosamente,</p><p>Sysprise Company</p><hr>";

    @RabbitListener(queues = "${spring.rabbitmq.queue_venda_material}")
    public void consumerBoletoCobranca(@Payload DadosEmailCobranca dadosEmailCobranca) throws MessagingException {
        byte[] bytesPdf = geradorBoleto.gerar(dadosEmailCobranca);
        enviarEmail.enviar(dadosEmailCobranca.pessoaId(), "Boleto", mensagemEmailCobranca, bytesPdf);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue_compra_material}")
    public void consumeroEmailCompra(@Payload DadosCompraMaterial dadosCompra) throws MessagingException {
        enviarEmail.enviar(dadosCompra.pessoaId(), "Compra de Material", montarPedido.montarPedido(dadosCompra));
    }
}
