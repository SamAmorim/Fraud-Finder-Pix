package org.uam.sdm.pixapi.integrations.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.uam.sdm.pixapi.domain.dto.transacoes.AnalisarTransacaoDto;
import org.uam.sdm.pixapi.integrations.AnaliseIntegration;

import reactor.core.publisher.Mono;

@Component
public class AnaliseIntegrationImpl implements AnaliseIntegration {

    private final WebClient webClient;

    @Value("${integration.analiseapi.url}")
    private String analiseApiUrl;

    public AnaliseIntegrationImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(analiseApiUrl).build();
    }

    @Override
    public Mono<Boolean> analisarPix(AnalisarTransacaoDto analisarTransacaoDto) {
        Mono<Boolean> resultado = webClient.post()
                .uri("/fazer_analise")
                .bodyValue(analisarTransacaoDto)
                .retrieve()
                .bodyToMono(Boolean.class);
        
        return resultado;
    }
}
