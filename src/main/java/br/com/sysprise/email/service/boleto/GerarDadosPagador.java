package br.com.sysprise.email.service.boleto;

import br.com.caelum.stella.boleto.Pagador;
import br.com.sysprise.email.model.DadosEmailCobranca;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import pb.*;

@Component
public class GerarDadosPagador {

    @GrpcClient("endereco")
    private EnderecoServiceGrpc.EnderecoServiceBlockingStub enderecoStub;

    @GrpcClient("pessoa")
    private PessoaServiceGrpc.PessoaServiceBlockingStub pessoaStub;


    public Pagador gerar(DadosEmailCobranca dadosCobranca) {
        DadosPessoa dadosPessoa = pessoaStub.getPersonData(PessoaId.newBuilder().setId(dadosCobranca.pessoaId()).build());

        pb.Endereco endereco = enderecoStub.getEnderecoById(EnderecoId.newBuilder().setId(dadosPessoa.getEnderecoId()).build());

        //Quem paga o boleto
        br.com.caelum.stella.boleto.Endereco enderecoPagador = br.com.caelum.stella.boleto.Endereco.novoEndereco()
                .comLogradouro(endereco.getRua())
                .comBairro(endereco.getBairro())
                .comCep(endereco.getCep())
                .comCidade("Cidade")
                .comUf("UF");

        return Pagador.novoPagador()
                .comNome(dadosPessoa.getNome())
                .comDocumento(dadosPessoa.getDocumento())
                .comEndereco(enderecoPagador);

    }
}
