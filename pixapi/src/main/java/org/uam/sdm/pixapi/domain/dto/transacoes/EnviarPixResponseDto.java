package org.uam.sdm.pixapi.domain.dto.transacoes;

import java.math.BigDecimal;
import java.util.UUID;

public record EnviarPixResponseDto(
    UUID id,
    BigDecimal valor,
    String nomeContaDestinoCliente,
    String registroNacionalContaDestinoCliente,
    String nomeContaDestinoInstituicao,
    Integer idFinalidadePix,
    String nomeFinalidadePix,
    Integer idTipoIniciacaoPix,
    String nomeTipoIniciacaoPix,
    String data,
    String mensagem
) { }
