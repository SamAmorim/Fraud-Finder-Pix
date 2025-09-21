package org.uam.sdm.pixapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uam.sdm.pixapi.domain.dto.contas.ObterContaPorChavePixResponse;
import org.uam.sdm.pixapi.services.ContasService;

@RestController(value = "/contas")
public class ContasController {

    private final ContasService contasService;

    public ContasController(ContasService contasService) {
        this.contasService = contasService;
    }

    @GetMapping("/chavePix/{chavePix}")
    public ObterContaPorChavePixResponse obterPorChavePix(String chavePix) {
        return contasService.obterPorChavePix(chavePix);
    }
}
