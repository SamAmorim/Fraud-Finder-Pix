package org.uam.sdm.pixapi.services.impl;

import org.springframework.stereotype.Service;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;
import org.uam.sdm.pixapi.repository.TransacoesRepository;
import org.uam.sdm.pixapi.services.TransacoesService;

@Service
public class TransacoesServiceImpl implements TransacoesService {

    private final TransacoesRepository transacoesRepository;

    public TransacoesServiceImpl(TransacoesRepository transacoesRepository) {
        this.transacoesRepository = transacoesRepository;
    }

    @Override
    public EnviarPixResponseDto enviarPix(EnviarPixRequestDto requestDto) {
        return new EnviarPixResponseDto();
    }
}
