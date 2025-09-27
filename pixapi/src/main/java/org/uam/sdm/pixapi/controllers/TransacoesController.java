package org.uam.sdm.pixapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;
import org.uam.sdm.pixapi.services.TransacoesService;

@RestController
@RequestMapping("transacoes")
public class TransacoesController {

    private final TransacoesService transacoesService;

    public TransacoesController(TransacoesService transacoesService) {
        this.transacoesService = transacoesService;
    }

    @PostMapping("/pix")
    public ResponseEntity<EnviarPixResponseDto> enviarPix(@RequestBody EnviarPixRequestDto requestDto) {
        var resposta = transacoesService.enviarPix(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
}
