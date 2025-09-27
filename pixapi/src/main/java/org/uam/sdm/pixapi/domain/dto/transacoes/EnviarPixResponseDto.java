package org.uam.sdm.pixapi.domain.dto.transacoes;

import java.math.BigDecimal;
import java.util.UUID;

public record EnviarPixResponseDto(
    UUID id,
    BigDecimal valor,
    String nomeContaDestinoCliente,
    String registroNacionalContaDestinoCliente,
    String nomeContaDestinoInstituicao,
    int idFinalidadePix,
    String nomeFinalidadePix,
    int idTipoIniciacaoPix,
    String nomeTipoIniciacaoPix,
    String data,
    String mensagem
) { }
