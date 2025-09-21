package org.uam.sdm.pixapi.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;
import org.uam.sdm.pixapi.services.TransacoesService;

@RestController(value = "/transacoes")
public class TransacoesController {

    private final TransacoesService transacoesService;

    public TransacoesController(TransacoesService transacoesService) {
        this.transacoesService = transacoesService;
    }

    @PostMapping("/pix")
    public EnviarPixResponseDto enviarPix(EnviarPixRequestDto requestDto) {
        return transacoesService.enviarPix(requestDto);
    }
}
