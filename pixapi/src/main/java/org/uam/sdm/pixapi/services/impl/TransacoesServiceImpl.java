package org.uam.sdm.pixapi.services.impl;

import org.springframework.stereotype.Service;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;
import org.uam.sdm.pixapi.mappers.TransacoesMapper;
import org.uam.sdm.pixapi.repository.TransacoesRepository;
import org.uam.sdm.pixapi.services.TransacoesService;

@Service
public class TransacoesServiceImpl implements TransacoesService {

    private final TransacoesRepository transacoesRepository;
    private final TransacoesMapper transacoesMapper;

    public TransacoesServiceImpl(TransacoesRepository transacoesRepository, TransacoesMapper transacoesMapper) {
        this.transacoesRepository = transacoesRepository;
        this.transacoesMapper = transacoesMapper;
    }

    @Override
    public EnviarPixResponseDto enviarPix(EnviarPixRequestDto requestDto) {
        var transacao = transacoesMapper.enviarPixRequestDtoToTransacao(requestDto);
        transacao = transacoesRepository.save(transacao);
        var resposta = transacoesMapper.transacaoToEnviarPixResponseDto(transacao);
        return resposta;
    }
}
