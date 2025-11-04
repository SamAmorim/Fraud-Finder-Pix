package org.uam.sdm.pixapi.services;

import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;

public interface TransacoesService {
    EnviarPixResponseDto enviarPix(EnviarPixRequestDto requestDto);
}
