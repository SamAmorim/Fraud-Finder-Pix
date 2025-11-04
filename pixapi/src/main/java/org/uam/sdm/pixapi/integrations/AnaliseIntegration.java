package org.uam.sdm.pixapi.integrations;

import org.uam.sdm.pixapi.domain.dto.transacoes.AnalisarTransacaoDto;

import reactor.core.publisher.Mono;

public interface AnaliseIntegration {
    Mono<Boolean> analisarPix(AnalisarTransacaoDto analisarTransacaoDto);
}
