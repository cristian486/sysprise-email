package br.com.sysprise.email.service;

import br.com.sysprise.email.model.DadosCompraMaterial;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import pb.ProdutoId;
import pb.ProdutoServiceGrpc;

import java.time.format.DateTimeFormatter;

@Component
public class MontarPedidoCompra {

    @GrpcClient("produto")
    private ProdutoServiceGrpc.ProdutoServiceBlockingStub produtoStub;

    public String montarPedido(DadosCompraMaterial dadosCompra) {
        StringBuilder sb = new StringBuilder("<h2>Prezado Fornecedor,</h2>");
        sb.append("<p>Estamos interessados em adquirir os seguintes materiais:</p>");
        sb.append("<table style='border-collapse: collapse;'>");

        sb.append("<tr>");
        sb.append("<th style='border: 1px solid black; padding: 5px;'>Material</th>");
        sb.append("<th style='border: 1px solid black; padding: 5px;'>Quantidade</th>");
        sb.append("</tr>");

        dadosCompra.produtoQuantidade().forEach((prodId, qtd) -> {
            String nomeDoProduto = produtoStub.getProductname(ProdutoId.newBuilder().setProdutoId(prodId).build()).getNome();
            sb.append("<tr>");
            sb.append("<td style='border: 1px solid black; padding: 5px;'>");
            sb.append(nomeDoProduto);
            sb.append("</td>");
            sb.append("<td style='border: 1px solid black; padding: 5px;'>");
            sb.append(qtd);
            sb.append("</td>");
        });

        sb.append("</table>");
        sb.append("<p>Por favor, envie-nos um orçamento detalhado para os materiais acima mencionados.</p>");
        sb.append("<p>Seria necessário a entrega destes materiais até o dia ");
        sb.append(dadosCompra.dataDeRecebimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sb.append("</p>");
        sb.append("<p>Qualquer dúvida, estamos à disposição.</p>");
        sb.append("<p>Atenciosamente,</p>");
        sb.append("<p>Equipe da Sysprise</p>");

        return sb.toString();
    }
}
