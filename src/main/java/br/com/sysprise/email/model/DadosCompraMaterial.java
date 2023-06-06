package br.com.sysprise.email.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.Map;

public record DadosCompraMaterial(Long pessoaId,
                                  Map<Long, Double> produtoQuantidade,
                                  @JsonDeserialize(using = LocalDateDeserializer.class)
                                  LocalDate dataDeRecebimento) {
}