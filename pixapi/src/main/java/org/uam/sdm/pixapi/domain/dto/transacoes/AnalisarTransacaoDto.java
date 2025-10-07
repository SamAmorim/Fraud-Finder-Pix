package org.uam.sdm.pixapi.domain.dto.transacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AnalisarTransacaoDto(
    BigDecimal valor,
    LocalDateTime data,
    int idTipoChavePix,
    LocalDateTime cadastradaEmChavePix,
    LocalDateTime abertaEmContaOrigem,
    int idNaturezaClienteContaOrigem,
    LocalDateTime nascidoEmClienteContaOrigem,
    int idTipoContaOrigem,
    String ispbInstituicaoContaOrigem,
    LocalDateTime abertaEmContaDestino,
    int idNaturezaClienteContaDestino,
    LocalDateTime nascidoEmClienteContaDestino,
    int idTipoContaDestino,
    String ispbInstituicaoContaDestino,
    int idTipoIniciacaoPix,
    int idFinalidadePix,
    String mensagem
) {

}
