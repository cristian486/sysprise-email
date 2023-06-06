package br.com.sysprise.email.service.email;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import pb.Contato;
import pb.ContatoServiceGrpc;
import pb.PessoaId;

@Component
public class BuscarContato {

    @GrpcClient("contato")
    private ContatoServiceGrpc.ContatoServiceBlockingStub contatoStub;


    public Contato buscarContato(Long pessoaId) {
        return contatoStub.getContatoByPessoaId(PessoaId.newBuilder().setId(pessoaId).build())
                .getContatoList()
                .stream()
                .filter(c -> !c.getEmail().isEmpty()).findFirst()
                .orElseThrow(() -> new IllegalStateException("Não foi possível encontrar um e-mail válido do cliente"));
    }
}
