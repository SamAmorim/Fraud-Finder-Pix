package org.uam.sdm.pixapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransacoesController {

    @GetMapping
    public String getHello() {
        return "Olá Mundo";
    }
}
