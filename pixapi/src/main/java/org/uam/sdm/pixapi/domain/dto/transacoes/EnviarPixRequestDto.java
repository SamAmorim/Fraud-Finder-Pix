package org.uam.sdm.pixapi.domain.dto.transacoes;

import java.math.BigDecimal;
import java.util.UUID;

public record EnviarPixRequestDto(
    BigDecimal valor,
    UUID idContaOrigem,
    UUID idContaDestino,
    int idFinalidadePix,
    int idTipoIniciacaoPix,
    String mensagem
) { }
