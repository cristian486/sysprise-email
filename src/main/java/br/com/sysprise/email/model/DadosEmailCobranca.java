package br.com.sysprise.email.model;

import java.io.Serializable;
import java.math.BigDecimal;

public record DadosEmailCobranca(Long pessoaId, BigDecimal valor) implements Serializable {
}
