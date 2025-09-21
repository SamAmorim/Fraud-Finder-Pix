package org.uam.sdm.pixapi.services.impl;

import org.uam.sdm.pixapi.domain.dto.contas.ObterContaPorChavePixResponse;
import org.uam.sdm.pixapi.repository.ContasRepository;
import org.uam.sdm.pixapi.services.ContasService;

public class ContasServiceImpl implements ContasService {

    private final ContasRepository contasRepository;

    public ContasServiceImpl(ContasRepository contasRepository) {
        this.contasRepository = contasRepository;
    }

    @Override
    public ObterContaPorChavePixResponse obterPorChavePix(String chavePix) {
        var conta = contasRepository.findByChavePix(chavePix);
        
        return new ObterContaPorChavePixResponse(
            conta.getId(),
            conta.getCliente().getNome(),
            conta.getCliente().getRegistroNacional(),
            conta.getInstituicao().getNome()
        );
    }
}
