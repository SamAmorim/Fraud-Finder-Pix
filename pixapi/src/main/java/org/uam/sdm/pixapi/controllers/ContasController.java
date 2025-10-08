package org.uam.sdm.pixapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uam.sdm.pixapi.domain.dto.contas.ObterContaPorChavePixResponse;
import org.uam.sdm.pixapi.services.ContasService;

@RestController
@RequestMapping("contas")
public class ContasController {

    private final ContasService contasService;

    public ContasController(ContasService contasService) {
        this.contasService = contasService;
    }

    @GetMapping("/chavePix/{chavePix}")
    public ResponseEntity<ObterContaPorChavePixResponse> obterPorChavePix(@PathVariable("chavePix") String chavePix) {
        var resposta = contasService.obterPorChavePix(chavePix);
        return ResponseEntity.ok(resposta);
    }
}
