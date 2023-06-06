package br.com.sysprise.email.service.boleto;

import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.sysprise.email.model.DadosEmailCobranca;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@Service
@AllArgsConstructor
public class GeradorBoleto {


    private GerarDadosPagador gerarDadosPagador;
    private GerarDadosBeneficiario gerarDadosBeneficiario;

    public byte[] gerar(DadosEmailCobranca dadosCobranca) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataDePagamento = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        Datas datas = Datas.novasDatas()
                .comDocumento(dataAtual.getDayOfMonth(), dataAtual.getMonth().getValue(), dataAtual.getYear())
                .comProcessamento(dataAtual.getDayOfMonth(), dataAtual.getMonth().getValue(), dataAtual.getYear())
                .comVencimento(dataDePagamento.getDayOfMonth(), dataDePagamento.getMonth().getValue(), dataDePagamento.getYear());

        Beneficiario beneficiario = gerarDadosBeneficiario.gerar();

        Pagador pagador = gerarDadosPagador.gerar(dadosCobranca);

        Banco banco = new BancoDoBrasil();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto(dadosCobranca.valor())
                .comNumeroDoDocumento("1234")
                //.comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")
                .comLocaisDePagamento("Qualquer instituição de pagamento física ou virutal");

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        gerador.geraPDF("BancoDoBrasil.pdf");
        return gerador.geraPDF();
    }
}
