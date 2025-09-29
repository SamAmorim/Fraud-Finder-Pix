package org.uam.sdm.pixapi.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;
import org.uam.sdm.pixapi.mappers.TransacoesMapper;
import org.uam.sdm.pixapi.repository.ContasRepository;
import org.uam.sdm.pixapi.repository.FinalidadesPixRepository;
import org.uam.sdm.pixapi.repository.TiposIniciacaoPixRepository;
import org.uam.sdm.pixapi.repository.TransacoesRepository;
import org.uam.sdm.pixapi.services.TransacoesService;

@Service
public class TransacoesServiceImpl implements TransacoesService {

    private final ContasRepository contasRepository;
    private final FinalidadesPixRepository finalidadesPixRepository;
    private final TiposIniciacaoPixRepository tiposIniciacaoPixRepository;
    private final TransacoesRepository transacoesRepository;
    private final TransacoesMapper transacoesMapper;

    public TransacoesServiceImpl(
            ContasRepository contasRepository,
            FinalidadesPixRepository finalidadesPixRepository,
            TiposIniciacaoPixRepository tiposIniciacaoPixRepository,
            TransacoesRepository transacoesRepository,
            TransacoesMapper transacoesMapper) {

        this.contasRepository = contasRepository;
        this.finalidadesPixRepository = finalidadesPixRepository;
        this.tiposIniciacaoPixRepository = tiposIniciacaoPixRepository;
        this.transacoesRepository = transacoesRepository;
        this.transacoesMapper = transacoesMapper;
    }

    @Override
    public EnviarPixResponseDto enviarPix(EnviarPixRequestDto requestDto) {
        var transacao = transacoesMapper.enviarPixRequestDtoToTransacao(requestDto);

        var contaOrigem = contasRepository
                .findById(requestDto.idContaOrigem())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta de origem não encontrada"));

        transacao.setContaOrigem(contaOrigem);

        var contaDestino = contasRepository
                .findById(requestDto.idContaDestino())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta de destino não encontrada"));

        transacao.setContaDestino(contaDestino);

        var finalidadePix = finalidadesPixRepository
                .getReferenceById(requestDto.idFinalidadePix());

        transacao.setFinalidadePix(finalidadePix);

        var tipoIniciacaoPix = tiposIniciacaoPixRepository
                .getReferenceById(requestDto.idTipoIniciacaoPix());

        transacao.setTipoIniciacaoPix(tipoIniciacaoPix);

        transacao = transacoesRepository.save(transacao);
        var resposta = transacoesMapper.transacaoToEnviarPixResponseDto(transacao);
        return resposta;
    }
}
