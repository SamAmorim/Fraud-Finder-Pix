package org.uam.sdm.pixapi.domain.dto.transacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AnalisarTransacaoDto(
    BigDecimal valor,
    LocalDateTime data,
    LocalDateTime abertaEmContaOrigem,
    int idNaturezaClienteContaOrigem,
    int idTipoContaOrigem,
    String ispbInstituicaoContaOrigem,
    LocalDateTime abertaEmContaDestino,
    int idNaturezaClienteContaDestino,
    int idTipoContaDestino,
    String ispbInstituicaoContaDestino,
    int idTipoIniciacaoPix,
    int idFinalidadePix,
    String mensagem
) {

}
