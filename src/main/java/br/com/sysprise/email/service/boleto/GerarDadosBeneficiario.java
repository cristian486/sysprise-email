package br.com.sysprise.email.service.boleto;

import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Endereco;
import org.springframework.stereotype.Component;

@Component
public class GerarDadosBeneficiario {

    public Beneficiario gerar() {
        //Quem emite o boleto
        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro("Av XXXXXX, 999")
                .comBairro("Centro")
                .comCep("81900-000")
                .comCidade("Cidade")
                .comUf("XX");


        return Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("Sysprise")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("18")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");
    }
}
