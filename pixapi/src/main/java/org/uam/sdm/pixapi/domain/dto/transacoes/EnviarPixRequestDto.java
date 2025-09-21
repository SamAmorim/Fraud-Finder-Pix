package org.uam.sdm.pixapi.domain.dto.transacoes;

import java.math.BigDecimal;

public record EnviarPixRequestDto(
    BigDecimal valor,
    int idContaOrigem,
    int idContaDestino,
    int idFinalidadePix,
    int idTipoIniciacaoPix,
    String mensagem
) { }
