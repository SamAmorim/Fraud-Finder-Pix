package org.uam.sdm.pixapi.services;

import org.uam.sdm.pixapi.domain.dto.contas.ObterContaPorChavePixResponse;

public interface ContasService {
    ObterContaPorChavePixResponse obterPorChavePix(String chavePix);
}
